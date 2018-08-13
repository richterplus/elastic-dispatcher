package com.github.richterplus.elastic.dispatcher;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 任务分派
 */
public class JobAssign {

    /**
     * 工作者名称
     */
    private String workerName;

    /**
     * 任务
     */
    private List<Job> jobs;

    private JobAssign() {

    }

    /**
     * 分派单个任务
     * @param workerName 工作者名称
     * @param jobNo 任务编号
     * @param jobInfo 任务信息
     * @param expectedStart 期望的开始时间
     */
    public static JobAssign singleJob(String workerName, String jobNo, String jobInfo, Date expectedStart) {
        return multiJobs(workerName, Collections.singletonList(jobNo), Collections.singletonList(jobInfo), expectedStart);
    }

    /**
     * 多个任务
     * @param workerName 工作者名称
     * @param jobNos 外部任务编号集合
     * @param jobInfos 任务信息集合
     * @param expectedStart 期望的任务开始时间，不能为null
     */
    public static JobAssign multiJobs(String workerName, Collection<String> jobNos, Collection<String> jobInfos, Date expectedStart) {
        if (workerName == null) {
            throw new RuntimeException("Elastic dispatcher job workerName must not be null.");
        }

        if (jobNos == null) {
            throw new RuntimeException("Elastic dispatcher job jobNo must not be null.");
        }

        if (jobInfos == null) {
            throw new RuntimeException("Elastic dispatcher job jobInfo must not be null.");
        }

        if (jobNos.size() != jobInfos.size()) {
            throw new RuntimeException("Elastic dispatcher job jobNo and jobInfo size not match.");
        }

        if (expectedStart == null) {
            throw new RuntimeException("Elastic dispatcher job expectedStart must not be null.");
        }

        if (jobNos.stream().anyMatch(StringUtils::isEmpty)) {
            throw new RuntimeException("Elastic dispatcher job jobNo must not be null.");
        }

        return new JobAssign() {
            {
                setWorkerName(workerName);
                setJobs(new ArrayList<>());
                Iterator<String> jobNoIterator = jobNos.iterator();
                Iterator<String> jobInfoIterator = jobInfos.iterator();
                while (jobNoIterator.hasNext()) {
                    getJobs().add(new Job() {
                        {
                            setWorkerName(workerName);
                            setJobUuid(UUID.randomUUID().toString());
                            setJobNo(jobNoIterator.next());
                            setJobInfo(StringUtils.defaultIfEmpty(jobInfoIterator.next(), ""));
                            setExpectedStart(expectedStart);
                            setAcceptableStart(expectedStart);
                            setMaxRetry(0);
                            setTimeout(120);
                        }
                    });
                }
            }
        };
    }

    /**
     * 设定可接受的任务开始时间
     * @param acceptableStart 任务开始时间，需要大于期望的任务开始时间
     */
    public JobAssign acceptableStart(Date acceptableStart) {
        if (acceptableStart == null) {
            jobs.forEach(job -> job.setAcceptableStart(job.getExpectedStart()));
        } else {
            if (jobs.size() > 0) {
                if (acceptableStart.before(jobs.get(0).getExpectedStart())) {
                    throw new RuntimeException("Elastic dispatcher job acceptableStart must not before expectedStart.");
                }
                long milliseconds = acceptableStart.getTime() - jobs.get(0).getExpectedStart().getTime();
                for (int i = 0; i < jobs.size(); i++) {
                    jobs.get(i).setAcceptableStart(new Date(jobs.get(i).getExpectedStart().getTime() + milliseconds * (i + 1) / jobs.size()));
                }
            }
        }
        return this;
    }

    /**
     * 设定最大重试次数
     * @param maxRetry 最大重试次数
     */
    public JobAssign maxRetry(Integer maxRetry) {
        if (maxRetry != null) jobs.forEach(job -> job.setMaxRetry(maxRetry));
        return this;
    }

    /**
     * 设定超时秒数
     * @param timeout 超时秒数
     */
    public JobAssign timeout(Integer timeout) {
        if (timeout != null) jobs.forEach(job -> job.setTimeout(timeout));
        return this;
    }


    /**
     * @return 工作者名称
     */
    public String getWorkerName() {
        return workerName;
    }

    /**
     * @param workerName 工作者名称
     */
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    /**
     * @return 任务
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     * @param jobs 任务
     */
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
