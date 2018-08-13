package com.github.richterplus.elastic.dispatcher;

/**
 * 任务启动监听
 */
public interface JobStartListener {

    /**
     * 任务启动
     * @param job 任务
     */
    void onJobStart(Job job);
}
