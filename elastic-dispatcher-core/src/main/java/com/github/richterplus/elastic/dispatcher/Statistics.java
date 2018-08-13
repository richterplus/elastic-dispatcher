package com.github.richterplus.elastic.dispatcher;

import java.util.List;

/**
 * 统计信息
 */
public class Statistics {

    /**
     * 全局最大线程数
     */
    private int globalMaxThreads;

    /**
     * 全局当前线程数
     */
    private int globalCurrentThreads;

    /**
     * 工作者信息
     */
    private List<Worker> workers;

    /**
     * @return 全局最大线程数
     */
    public int getGlobalMaxThreads() {
        return globalMaxThreads;
    }

    /**
     * @param globalMaxThreads 全局最大线程数
     */
    public void setGlobalMaxThreads(int globalMaxThreads) {
        this.globalMaxThreads = globalMaxThreads;
    }

    /**
     * @return 全局当前线程数
     */
    public int getGlobalCurrentThreads() {
        return globalCurrentThreads;
    }

    /**
     * @param globalCurrentThreads 全局当前线程数
     */
    public void setGlobalCurrentThreads(int globalCurrentThreads) {
        this.globalCurrentThreads = globalCurrentThreads;
    }

    /**
     * @return 工作者信息
     */
    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * @param workers 工作者信息
     */
    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
