package com.github.richterplus.elastic.dispatcher.jdbc.entity;

/**
 * 任务
 */
public class ElasticDispatcherJob {

    /**
     * 任务id
     */
    private Integer jobId;

    /**
     * 任务uuid
     */
    private String jobUuid;

    /**
     * 外部任务编号
     */
    private String jobNo;

    /**
     * 工作者id
     */
    private Integer workerId;

    /**
     * 任务信息
     */
    private String jobInfo;

    /**
     * 任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）
     */
    private Integer jobState;

    /**
     * 期望开始日期
     */
    private java.util.Date expectedStart;

    /**
     * 可接受的开始日期
     */
    private java.util.Date acceptableStart;

    /**
     * 超时时间
     */
    private java.util.Date expireDate;

    /**
     * 实际开始时间
     */
    private java.util.Date actualStart;

    /**
     * 实际结束时间
     */
    private java.util.Date actualEnd;

    /**
     * 最大重试次数
     */
    private Integer maxRetry;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 任务创建日期
     */
    private java.util.Date createDate;

    /**
     * 获取任务id
     * @return 任务id
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * 设置任务id
     * @param jobId 任务id
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取任务uuid
     * @return 任务uuid
     */
    public String getJobUuid() {
        return jobUuid;
    }

    /**
     * 设置任务uuid
     * @param jobUuid 任务uuid
     */
    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    /**
     * 获取外部任务编号
     * @return 外部任务编号
     */
    public String getJobNo() {
        return jobNo;
    }

    /**
     * 设置外部任务编号
     * @param jobNo 外部任务编号
     */
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

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
     * 获取任务信息
     * @return 任务信息
     */
    public String getJobInfo() {
        return jobInfo;
    }

    /**
     * 设置任务信息
     * @param jobInfo 任务信息
     */
    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    /**
     * 获取任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）
     * @return 任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）
     */
    public Integer getJobState() {
        return jobState;
    }

    /**
     * 设置任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）
     * @param jobState 任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）
     */
    public void setJobState(Integer jobState) {
        this.jobState = jobState;
    }

    /**
     * 获取期望开始日期
     * @return 期望开始日期
     */
    public java.util.Date getExpectedStart() {
        return expectedStart;
    }

    /**
     * 设置期望开始日期
     * @param expectedStart 期望开始日期
     */
    public void setExpectedStart(java.util.Date expectedStart) {
        this.expectedStart = expectedStart;
    }

    /**
     * 获取可接受的开始日期
     * @return 可接受的开始日期
     */
    public java.util.Date getAcceptableStart() {
        return acceptableStart;
    }

    /**
     * 设置可接受的开始日期
     * @param acceptableStart 可接受的开始日期
     */
    public void setAcceptableStart(java.util.Date acceptableStart) {
        this.acceptableStart = acceptableStart;
    }

    /**
     * 获取超时时间
     * @return 超时时间
     */
    public java.util.Date getExpireDate() {
        return expireDate;
    }

    /**
     * 设置超时时间
     * @param expireDate 超时时间
     */
    public void setExpireDate(java.util.Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * 获取实际开始时间
     * @return 实际开始时间
     */
    public java.util.Date getActualStart() {
        return actualStart;
    }

    /**
     * 设置实际开始时间
     * @param actualStart 实际开始时间
     */
    public void setActualStart(java.util.Date actualStart) {
        this.actualStart = actualStart;
    }

    /**
     * 获取实际结束时间
     * @return 实际结束时间
     */
    public java.util.Date getActualEnd() {
        return actualEnd;
    }

    /**
     * 设置实际结束时间
     * @param actualEnd 实际结束时间
     */
    public void setActualEnd(java.util.Date actualEnd) {
        this.actualEnd = actualEnd;
    }

    /**
     * 获取最大重试次数
     * @return 最大重试次数
     */
    public Integer getMaxRetry() {
        return maxRetry;
    }

    /**
     * 设置最大重试次数
     * @param maxRetry 最大重试次数
     */
    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    /**
     * 获取重试次数
     * @return 重试次数
     */
    public Integer getRetryTimes() {
        return retryTimes;
    }

    /**
     * 设置重试次数
     * @param retryTimes 重试次数
     */
    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    /**
     * 获取失败原因
     * @return 失败原因
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * 设置失败原因
     * @param failReason 失败原因
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    /**
     * 获取任务创建日期
     * @return 任务创建日期
     */
    public java.util.Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置任务创建日期
     * @param createDate 任务创建日期
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

}