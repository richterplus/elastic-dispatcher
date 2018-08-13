package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 创建工作者请求
 */
public class CreateWorkerReq extends Req {

    /**
     * 工作者名称
     */
    @NotNull
    @ApiModelProperty("工作者名称")
    private String name;

    /**
     * 最大线程数
     */
    @NotNull
    @Min(1)
    @ApiModelProperty("最大线程数")
    private Integer maxThreads;

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
}
