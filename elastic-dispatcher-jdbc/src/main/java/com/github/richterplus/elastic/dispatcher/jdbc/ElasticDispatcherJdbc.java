package com.github.richterplus.elastic.dispatcher.jdbc;

import com.github.richterplus.elastic.dispatcher.*;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherConfig;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherJob;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherWorker;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherConfigRepository;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherJobRepository;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherWorkerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
class ElasticDispatcherJdbc implements ElasticDispatcher {

    /**
     * 时钟每一跳为1000毫秒（与PER_FETCH_COUNT结合表示每TICK最大处理PER_FETCH_COUNT）
     */
    private static final int TICK = 1000;

    /**
     * 每次最大获取数量（与TICK结合表示每TICK最大处理PER_FETCH_COUNT）
     */
    private static final int PER_FETCH_COUNT = 30;

    private static final String CONFIG_THREADS = "threads";

    private JobStartListener jobStartListener;

    private JobFailListener jobFailListener;

    @Autowired
    private ElasticDispatcherConfigRepository configRepository;

    @Autowired
    private ElasticDispatcherJobRepository jobRepository;

    @Autowired
    private ElasticDispatcherWorkerRepository workerRepository;

    @Autowired
    private ElasticDispatcherService elasticDispatcherService;

    private final Object mutex = new Object();

    private Thread tickThread;

    private final Map<String, JobThread> jobUuidToJobThread = new ConcurrentHashMap<>();

    private void start() {
        //启动轮询，如果轮询没有启动的话
        synchronized (mutex) {
            if (tickThread == null) {
                tickThread = new Thread(() -> {
                    while (true) {

                        //发现需要执行的任务并执行
                        try {
                            //获取所有线程已满的worker
                            List<ElasticDispatcherWorker> fullThreadsWorkers = workerRepository.listByMaxThreadsEqCurrentThreads();

                            //按顺序取得前PER_FETCH_COUNT个可以执行的job
                            List<ElasticDispatcherJob> jobs = jobRepository.listAndSortByAcceptableStartAsc(
                                    JobState.NOT_STARTED,
                                    new Date(),
                                    fullThreadsWorkers.stream().map(ElasticDispatcherWorker::getWorkerId).collect(Collectors.toList()),
                                    PER_FETCH_COUNT);

                            //分配线程给每个job执行
                            for (ElasticDispatcherJob job : jobs) {
                                Thread thread = new Thread(() -> {
                                    try {
                                        elasticDispatcherService.runJob(job, CONFIG_THREADS, jobStartListener, jobFailListener);
                                        jobUuidToJobThread.remove(job.getJobUuid());
                                    } catch (Exception e) {
                                        LoggerFactory.getLogger("").error("Error when run elastic dispatcher job " + job.getJobUuid(), e);
                                    }
                                });
                                jobUuidToJobThread.put(job.getJobUuid(), new JobThread() {
                                    {
                                        setJob(job);
                                        setThread(thread);
                                    }
                                });
                                thread.start();
                            }

                        } catch (Exception e) {
                            LoggerFactory.getLogger("").error("Error when run elastic dispatcher jobs.", e);
                        }

                        //尝试终止已经超时的线程
                        Date now = new Date();
                        Set<String> jobUuids = jobUuidToJobThread.keySet();
                        for (String jobUuid : jobUuids) {
                            JobThread jobThread = jobUuidToJobThread.get(jobUuid);
                            if (jobThread.getJob().getExpireDate().before(now)) {
                                jobThread.getThread().interrupt();
                                jobUuidToJobThread.remove(jobUuid);
                            }
                        }

                        //发现超时的任务并终止
                        try {
                            //获取所有已超时的任务
                            List<ElasticDispatcherJob> timeoutJobs = jobRepository.listByJobStateAndMaxExpireDate(JobState.RUNNING, now);

                            for (ElasticDispatcherJob job : timeoutJobs) {
                                new Thread(() -> {
                                    try {
                                        elasticDispatcherService.terminateJob(job, CONFIG_THREADS, jobFailListener);
                                    } catch (Exception e) {
                                        LoggerFactory.getLogger("").error("Error when terminate elastic dispatcher job " + job.getJobUuid(), e);
                                    }
                                }).start();
                            }
                        } catch (Exception e) {
                            LoggerFactory.getLogger("").error("Error when terminate elastic dispatcher jobs.", e);
                        }

                        try {
                            Thread.sleep(TICK);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                tickThread.start();
            }
        }
    }

    private void stop() {
        synchronized (mutex) {
            tickThread.interrupt();
            tickThread = null;
        }
    }

    @Override
    public void onJobStart(JobStartListener listener) {
        synchronized (mutex) {
            jobStartListener = listener;
            if (jobStartListener != null) {
                start();
            }
        }
    }

    @Override
    public void onJobFail(JobFailListener listener) {
        jobFailListener = listener;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public void setMaxThreads(int maxThreads) {
        ElasticDispatcherConfig config = configRepository.getByConfigName(CONFIG_THREADS);
        if (config == null) {
            throw new JdbcException("Can not find elastic dispatcher config data.");
        }
        config.setMaxValue(maxThreads);
        configRepository.update(config);
    }

    @Override
    public void assignJob(JobAssign jobAssign) {
        jobAssign.getJobs().forEach(job -> assignJob(jobAssign.getWorkerName(), job));
    }

    private void assignJob(String workerName, Job job) {
        ElasticDispatcherWorker worker = workerRepository.getByWorkerName(workerName);
        if (worker == null) {
            throw new JdbcException("Invalid elastic dispatcher workerName.");
        }

        if (jobRepository.getByJobNo(job.getJobNo()) != null) {
            throw new JdbcException("Duplicate elastic dispatcher jobNo.");
        }

        jobRepository.create(new ElasticDispatcherJob() {
            {
                setJobUuid(job.getJobUuid());
                setJobNo(job.getJobNo());
                setWorkerId(worker.getWorkerId());
                setJobInfo(job.getJobInfo());
                setJobState(JobState.NOT_STARTED);
                setExpectedStart(job.getExpectedStart());
                setAcceptableStart(job.getAcceptableStart());
                setMaxRetry(job.getMaxRetry());
                setRetryTimes(0);
                setExpireDate(Date.from(job.getAcceptableStart().toInstant().plusSeconds(job.getTimeout().longValue())));
                setFailReason("");
                setCreateDate(new Date());
            }
        });
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    @Override
    public void finishJob(String jobUuid) {

        ElasticDispatcherJob job = jobRepository.getByUuid(jobUuid);

        if (job != null) {
            //尝试更新任务状态
            if (jobRepository.updateJobState(job.getJobId(), JobState.COMPLETE, JobState.RUNNING)) {

                job.setJobState(JobState.COMPLETE);
                job.setActualEnd(new Date());
                jobRepository.update(job);

                ElasticDispatcherWorker worker = workerRepository.getByWorkerId(job.getWorkerId());

                //归还worker线程
                if (workerRepository.decrementCurrentThreads(worker.getWorkerId())) {
                    //归还全局线程
                    configRepository.decrementCurrentValue(CONFIG_THREADS);
                }
            }
        }
    }

    @Override
    public Worker getWorker(String workerName) {
        ElasticDispatcherWorker worker = workerRepository.getByWorkerName(workerName);
        return worker == null ? null : new Worker() {
            {
                setName(worker.getWorkerName());
                setMaxThreads(worker.getMaxThreads() == null ? 0 : worker.getMaxThreads());
            }
        };
    }

    @Override
    public void removeWorker(String workerName) {
        if (workerName != null) {
            ElasticDispatcherWorker worker = workerRepository.getByWorkerName(workerName);
            if (worker != null) {
                workerRepository.delete(worker.getWorkerId());
            }
        }
    }

    @Override
    public void createWorker(Worker worker) {
        workerRepository.create(new ElasticDispatcherWorker() {
            {
                setWorkerName(worker.getName());
                setMaxThreads(worker.getMaxThreads());
                setCurrentThreads(0);
            }
        });
    }

    @Override
    public void updateWorker(Worker worker) {
        ElasticDispatcherWorker elasticDispatcherWorker = workerRepository.getByWorkerName(worker.getName());
        if (elasticDispatcherWorker == null) {
            throw new JdbcException("Can not find elastic dispatcher worker.");
        }
        if (worker.getMaxThreads() != null) {
            elasticDispatcherWorker.setMaxThreads(worker.getMaxThreads());
        }
        if (worker.getCurrentThreads() != null) {
            elasticDispatcherWorker.setCurrentThreads(worker.getCurrentThreads());
        }
        workerRepository.update(elasticDispatcherWorker);
    }

    @Override
    public Statistics statistics() {
        ElasticDispatcherConfig config = configRepository.getByConfigName(CONFIG_THREADS);
        List<ElasticDispatcherWorker> workers = workerRepository.listByMinCurrentThreads(1);
        return new Statistics() {
            {
                setGlobalCurrentThreads(config.getCurrentValue());
                setGlobalMaxThreads(config.getMaxValue());
                setWorkers(workers.stream().map(worker -> new Worker() {
                    {
                        setName(worker.getWorkerName());
                        setCurrentThreads(worker.getCurrentThreads());
                        setMaxThreads(worker.getMaxThreads());
                    }
                }).collect(Collectors.toList()));
            }
        };
    }
}
