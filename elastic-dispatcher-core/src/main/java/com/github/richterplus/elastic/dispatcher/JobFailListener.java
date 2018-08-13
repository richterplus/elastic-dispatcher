package com.github.richterplus.elastic.dispatcher;

/**
 * 任务失败监听
 */
public interface JobFailListener {

    /**
     * 任务失败
     * @param job 任务
     */
    void onFail(Job job);
}
