package com.github.richterplus.elastic.dispatcher.jdbc.entity;

/**
 * 工作者
 */
public class ElasticDispatcherWorker {

    /**
     * 工作者id
     */
    private Integer workerId;

    /**
     * 工作者名称
     */
    private String workerName;

    /**
     * 最大工作线程数
     */
    private Integer maxThreads;

    /**
     * 当前工作线程数
     */
    private Integer currentThreads;

    /**
     * 获取工作者id
     * @return 工作者id
     */
    public Integer getWorkerId() {
        return workerId;
    }

    /**
     * 设置工作者id
     * @param workerId 工作者id
     */
    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    /**
     * 获取工作者名称
     * @return 工作者名称
     */
    public String getWorkerName() {
        return workerName;
    }

    /**
     * 设置工作者名称
     * @param workerName 工作者名称
     */
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    /**
     * 获取最大工作线程数
     * @return 最大工作线程数
     */
    public Integer getMaxThreads() {
        return maxThreads;
    }

    /**
     * 设置最大工作线程数
     * @param maxThreads 最大工作线程数
     */
    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    /**
     * 获取当前工作线程数
     * @return 当前工作线程数
     */
    public Integer getCurrentThreads() {
        return currentThreads;
    }

    /**
     * 设置当前工作线程数
     * @param currentThreads 当前工作线程数
     */
    public void setCurrentThreads(Integer currentThreads) {
        this.currentThreads = currentThreads;
    }

}