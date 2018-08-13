package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 创建任务请求
 */
public class CreateJobReq extends Req {

    /**
     * 外部任务编号
     */
    @NotNull
    @Size(min = 1, max = 128)
    @ApiModelProperty("外部任务编号")
    private String jobNo;

    /**
     * 任务信息
     */
    @NotNull
    @ApiModelProperty("任务信息")
    private String jobInfo;

    /**
     * 工作者名称
     */
    @NotNull
    @ApiModelProperty("工作者名称")
    private String workerName;

    /**
     * 期望的任务开始时间
     */
    @NotNull
    @ApiModelProperty("期望的任务开始时间")
    private Date expectedStart;

    /**
     * 可接受的任务开始时间
     */
    @ApiModelProperty("可接受的任务开始时间")
    private Date acceptableStart;

    /**
     * 最大重试次数
     */
    @Min(0)
    @ApiModelProperty("最大重试次数")
    private Integer maxRetry;

    /**
     * 超时秒数
     */
    @Min(1)
    @ApiModelProperty("超时秒数")
    private Integer timeout;

    /**
     * @return 外部任务编号
     */
    public String getJobNo() {
        return jobNo;
    }

    /**
     * @param jobNo 外部任务编号
     */
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    /**
     * @return 任务信息
     */
    public String getJobInfo() {
        return jobInfo;
    }

    /**
     * @param jobInfo 任务信息
     */
    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    /**
     * @return 工作者名称
     */
    public String getWorkerName() {
        return workerName;
    }

    /**
     * @param workerName 工作者名称
     */
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    /**
     * @return 期望的任务开始时间
     */
    public Date getExpectedStart() {
        return expectedStart;
    }

    /**
     * @param expectedStart 期望的任务开始时间
     */
    public void setExpectedStart(Date expectedStart) {
        this.expectedStart = expectedStart;
    }

    /**
     * @return 可接受的任务开始时间
     */
    public Date getAcceptableStart() {
        return acceptableStart;
    }

    /**
     * @param acceptableStart 可接受的任务开始时间
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
}
