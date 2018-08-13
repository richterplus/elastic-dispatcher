package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;

/**
 * 创建任务响应
 */
public class CreateJobsResp extends Resp {

    /**
     * 任务Uuid
     */
    @ApiModelProperty("任务Uuid")
    private Collection<String> jobUuids;

    /**
     * @return 任务Uuid
     */
    public Collection<String> getJobUuids() {
        return jobUuids;
    }

    /**
     * @param jobUuids 任务Uuid
     */
    public void setJobUuids(Collection<String> jobUuids) {
        this.jobUuids = jobUuids;
    }
}
