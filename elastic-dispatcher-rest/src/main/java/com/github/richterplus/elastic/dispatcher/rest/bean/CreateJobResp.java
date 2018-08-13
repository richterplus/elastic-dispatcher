package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 创建任务响应
 */
public class CreateJobResp extends Resp {

    /**
     * 任务Uuid
     */
    @ApiModelProperty("任务Uuid")
    private String JobUuid;

    /**
     * @return 任务Uuid
     */
    public String getJobUuid() {
        return JobUuid;
    }

    /**
     * @param jobUuid 任务Uuid
     */
    public void setJobUuid(String jobUuid) {
        JobUuid = jobUuid;
    }
}
