package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 请求
 */
public abstract class Req {

    /**
     * 请求日期
     */
    @ApiModelProperty("请求日期")
    private Date requestDate = new Date();

    /**
     * @return 请求日期
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate 请求日期
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
