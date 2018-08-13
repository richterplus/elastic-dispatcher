package com.github.richterplus.elastic.dispatcher;

/**
 * 弹性调度
 */
public interface ElasticDispatcher {

    /**
     * 任务启动监听
     */
    void onJobStart(JobStartListener listener);

    /**
     * 任务失败监听
     */
    void onJobFail(JobFailListener listener);

    /**
     * @param maxThreads 弹性调度框架的全局最大工作线程数
     */
    void setMaxThreads(int maxThreads);

    /**
     * 分配任务
     * @param jobAssign 任务分派
     */
    void assignJob(JobAssign jobAssign);

    /**
     * 完成任务
     * @param jobUuid 任务uuid
     */
    void finishJob(String jobUuid);

    /**
     * 获取工作者信息
     * @param workerName 工作者名称
     * @return 如果存在对应的工作者名称，则返回工作者，否则返回null
     */
    Worker getWorker(String workerName);

    /**
     * 删除工作者
     * @param workerName 工作者名称
     */
    void removeWorker(String workerName);

    /**
     * 添加工作者
     * @param worker 工作者
     */
    void createWorker(Worker worker);

    /**
     * 更新工作者
     * @param worker 工作者
     */
    void updateWorker(Worker worker);

    /**
     * 获取统计数据
     * @return 统计数据
     */
    Statistics statistics();
}
