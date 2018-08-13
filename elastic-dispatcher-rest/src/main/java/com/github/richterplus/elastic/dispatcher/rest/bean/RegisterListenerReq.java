package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 注册监听请求
 */
public class RegisterListenerReq extends Req {

    /**
     * 任务执行回调url
     */
    @NotNull
    @ApiModelProperty("任务执行回调url")
    private String url;

    /**
     * @return 任务执行回调url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 任务执行回调url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
