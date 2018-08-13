package com.github.richterplus.elastic.dispatcher.rest.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 响应
 */
public abstract class Resp {

    /**
     * 响应日期
     */
    @ApiModelProperty("响应日期")
    private Date responseDate = new Date();

    /**
     * @return 响应日期
     */
    public Date getResponseDate() {
        return responseDate;
    }

    /**
     * @param responseDate 响应日期
     */
    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }
}
