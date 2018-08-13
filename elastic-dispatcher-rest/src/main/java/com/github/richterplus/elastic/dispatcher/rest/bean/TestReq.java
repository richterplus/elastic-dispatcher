package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 测试请求
 */
public class TestReq extends Req {

    /**
     * 工作者数量
     */
    @NotNull
    @Min(1)
    @ApiModelProperty("工作者数量")
    private Integer workers;

    /**
     * 每个工作者的最少任务数量
     */
    @NotNull
    @Min(1)
    @ApiModelProperty("每个工作者的最少任务数量")
    private Integer minJobsPerWorker;

    /**
     * 每个工作者的最多任务数量
     */
    @NotNull
    @Min(1)
    @ApiModelProperty("每个工作者的最多任务数量")
    private Integer maxJobsPerWorker;

    /**
     * 最少工作线程数
     */
    @NotNull
    @Min(1)
    @ApiModelProperty("最少工作线程数")
    private Integer minWorkerThreads;

    /**
     * 最多工作线程数
     */
    @NotNull
    @Min(1)
    @ApiModelProperty("最多工作线程数")
    private Integer maxWorkerThreads;

    /**
     * 超时秒数
     */
    @NotNull
    @Min(10)
    @ApiModelProperty("超时秒数")
    private Integer timeout;

    /**
     * 期望开始时间
     */
    @NotNull
    @ApiModelProperty("期望开始时间")
    private Date expectedStart;

    /**
     * 可接受的开始时间
     */
    @NotNull
    @ApiModelProperty("可接受的开始时间")
    private Date acceptableStart;

    /**
     * 最大重试次数
     */
    @NotNull
    @Min(0)
    @ApiModelProperty("最大重试次数")
    private Integer maxRetry;

    /**
     * @return 工作者数量
     */
    public Integer getWorkers() {
        return workers;
    }

    /**
     * @param workers 工作者数量
     */
    public void setWorkers(Integer workers) {
        this.workers = workers;
    }

    /**
     * @return 每个工作者的最少任务数量
     */
    public Integer getMinJobsPerWorker() {
        return minJobsPerWorker;
    }

    /**
     * @param minJobsPerWorker 每个工作者的最少任务数量
     */
    public void setMinJobsPerWorker(Integer minJobsPerWorker) {
        this.minJobsPerWorker = minJobsPerWorker;
    }

    /**
     * @return 每个工作者的最多任务数量
     */
    public Integer getMaxJobsPerWorker() {
        return maxJobsPerWorker;
    }

    /**
     * @param maxJobsPerWorker 每个工作者的最多任务数量
     */
    public void setMaxJobsPerWorker(Integer maxJobsPerWorker) {
        this.maxJobsPerWorker = maxJobsPerWorker;
    }

    /**
     * @return 最少工作线程数
     */
    public Integer getMinWorkerThreads() {
        return minWorkerThreads;
    }

    /**
     * @param minWorkerThreads 最少工作线程数
     */
    public void setMinWorkerThreads(Integer minWorkerThreads) {
        this.minWorkerThreads = minWorkerThreads;
    }

    /**
     * @return 最多工作线程数
     */
    public Integer getMaxWorkerThreads() {
        return maxWorkerThreads;
    }

    /**
     * @param maxWorkerThreads 最多工作线程数
     */
    public void setMaxWorkerThreads(Integer maxWorkerThreads) {
        this.maxWorkerThreads = maxWorkerThreads;
    }

    /**
     * @return 超时秒数
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * @param timeout 超时秒数
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * @return 期望开始时间
     */
    public Date getExpectedStart() {
        return expectedStart;
    }

    /**
     * @param expectedStart 期望开始时间
     */
    public void setExpectedStart(Date expectedStart) {
        this.expectedStart = expectedStart;
    }

    /**
     * @return 可接受的开始时间
     */
    public Date getAcceptableStart() {
        return acceptableStart;
    }

    /**
     * @param acceptableStart 可接受的开始时间
     */
    public void setAcceptableStart(Date acceptableStart) {
        this.acceptableStart = acceptableStart;
    }

    /**
     * @return 最大重试次数
     */
    public Integer getMaxRetry() {
        return maxRetry;
    }

    /**
     * @param maxRetry 最大重试次数
     */
    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }
}
