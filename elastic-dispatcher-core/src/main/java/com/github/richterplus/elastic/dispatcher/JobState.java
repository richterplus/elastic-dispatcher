package com.github.richterplus.elastic.dispatcher;

/**
 * 任务状态
 */
public class JobState {

    /**
     * 取消
     */
    public static final int CANCELED = -1;

    /**
     * 未开始
     */
    public static final int NOT_STARTED = 0;

    /**
     * 进行中
     */
    public static final int RUNNING = 1;

    /**
     * 完成
     */
    public static final int COMPLETE = 2;

    /**
     * 失败
     */
    public static final int FAILED = -2;
}
