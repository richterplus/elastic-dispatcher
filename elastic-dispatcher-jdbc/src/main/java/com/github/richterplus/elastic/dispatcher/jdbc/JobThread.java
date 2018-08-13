package com.github.richterplus.elastic.dispatcher.jdbc;

import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherJob;

/**
 * 任务线程
 */
class JobThread {

    private ElasticDispatcherJob job;

    private Thread thread;

    public ElasticDispatcherJob getJob() {
        return job;
    }

    public void setJob(ElasticDispatcherJob job) {
        this.job = job;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
