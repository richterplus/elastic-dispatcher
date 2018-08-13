package com.github.richterplus.elastic.dispatcher.jdbc;

import com.github.richterplus.elastic.dispatcher.Job;
import com.github.richterplus.elastic.dispatcher.JobFailListener;
import com.github.richterplus.elastic.dispatcher.JobStartListener;
import com.github.richterplus.elastic.dispatcher.JobState;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherJob;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherWorker;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherConfigRepository;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherJobRepository;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class ElasticDispatcherService {

    @Autowired
    private ElasticDispatcherConfigRepository configRepository;

    @Autowired
    private ElasticDispatcherWorkerRepository workerRepository;

    @Autowired
    private ElasticDispatcherJobRepository jobRepository;

    private final Map<String, Thread> startThreads = new ConcurrentHashMap<>();

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public void runJob(ElasticDispatcherJob job, String CONFIG_THREADS, JobStartListener jobStartListener, JobFailListener jobFailListener) {
        if (job != null) {
            //尝试申请全局线程
            if (!configRepository.incrementCurrentValue(CONFIG_THREADS)) {
                throw new JdbcException("Can not fetch global thread");
            }

            ElasticDispatcherWorker worker = workerRepository.getByWorkerId(job.getWorkerId());
            if (worker == null) {
                job.setJobState(JobState.FAILED);
                job.setFailReason("Worker不存在，放弃任务");
                jobRepository.update(job);

                configRepository.decrementCurrentValue(CONFIG_THREADS);
            } else {

                //尝试分配线程给worker
                if (!workerRepository.incrementCurrentThreads(worker.getWorkerId())) {
                    throw new JdbcException("Can not fetch worker thread.");
                }

                //尝试锁定job防止被其他节点已经先行使用
                if (!jobRepository.updateJobState(job.getJobId(), JobState.RUNNING, JobState.NOT_STARTED)) {
                    throw new JdbcException("Can not update job running state.");
                }

                long timeout = job.getExpireDate().getTime() - (job.getActualStart() == null ? job.getAcceptableStart() : job.getActualStart()).getTime();

                try {
                    Date now = new Date();
                    job.setJobState(JobState.RUNNING);
                    job.setExpireDate(new Date(timeout + now.getTime()));
                    job.setActualStart(now);
                    jobRepository.update(job);

                    Thread thread = new Thread(() -> {
                        jobStartListener.onJobStart(new Job() {
                            {
                                setJobUuid(job.getJobUuid());
                                setJobInfo(job.getJobInfo());
                                setWorkerName(worker.getWorkerName());
                                setExpectedStart(job.getExpectedStart());
                                setAcceptableStart(job.getAcceptableStart());
                                setMaxRetry(job.getMaxRetry());
                            }
                        });
                        startThreads.remove(job.getJobUuid());
                    });

                    startThreads.put(job.getJobUuid(), thread);
                    thread.start();
                } catch (Exception e) {
                    //进行重试处理
                    terminateJob(job, CONFIG_THREADS, jobFailListener);
                }
            }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public void terminateJob(ElasticDispatcherJob job, String CONFIG_THREADS, JobFailListener jobFailListener) {
        if (job == null || !jobRepository.updateJobState(job.getJobId(), JobState.FAILED, JobState.RUNNING)) {
            throw new JdbcException("Can not update job fail state.");
        }

        Thread startThread = startThreads.remove(job.getJobUuid());
        if (startThread != null) startThread.interrupt();

        boolean failed = false;

        if (job.getRetryTimes() < job.getMaxRetry()) {
            job.setJobState(JobState.NOT_STARTED);
            job.setRetryTimes(job.getRetryTimes() + 1);
            job.setExpireDate(new Date(job.getExpireDate().getTime() - job.getActualStart().getTime() + new Date().getTime()));
            jobRepository.update(job);
        } else {
            job.setJobState(JobState.FAILED);
            job.setFailReason("任务执行失败");
            job.setActualEnd(new Date());
            jobRepository.update(job);
            failed = true;
        }

        ElasticDispatcherWorker worker = workerRepository.getByWorkerId(job.getWorkerId());
        if (worker != null) {
            workerRepository.decrementCurrentThreads(worker.getWorkerId());
        }

        configRepository.decrementCurrentValue(CONFIG_THREADS);

        if (jobFailListener != null && failed) {
            Thread thread = new Thread(() -> jobFailListener.onFail(new Job() {
                {
                    setJobUuid(job.getJobUuid());
                    setWorkerName(worker == null ? "已删除的worker" : worker.getWorkerName());
                    setJobNo(job.getJobNo());
                }
            }));
            thread.start();
        }
    }
}
