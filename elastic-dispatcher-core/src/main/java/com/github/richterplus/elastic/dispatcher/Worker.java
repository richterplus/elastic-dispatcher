package com.github.richterplus.elastic.dispatcher;

/**
 * 工作者
 */
public class Worker {

    /**
     * 工作者名称
     */
    private String name;

    /**
     * 最大线程数
     */
    private Integer maxThreads;

    /**
     * 当前线程数
     */
    private Integer currentThreads;

    /**
     * @return 工作者名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 工作者名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 最大线程数
     */
    public Integer getMaxThreads() {
        return maxThreads;
    }

    /**
     * @param maxThreads 最大线程数
     */
    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    /**
     * @return 当前线程数
     */
    public Integer getCurrentThreads() {
        return currentThreads;
    }

    /**
     * @param currentThreads 当前线程数
     */
    public void setCurrentThreads(Integer currentThreads) {
        this.currentThreads = currentThreads;
    }
}
