package com.github.richterplus.elastic.dispatcher.jdbc.entity;

/**
 * 全局配置
 */
public class ElasticDispatcherConfig {

    /**
     * 配置id
     */
    private Integer configId;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 最大值
     */
    private Integer maxValue;

    /**
     * 当前值
     */
    private Integer currentValue;

    /**
     * 获取配置id
     * @return 配置id
     */
    public Integer getConfigId() {
        return configId;
    }

    /**
     * 设置配置id
     * @param configId 配置id
     */
    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    /**
     * 获取配置名称
     * @return 配置名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置配置名称
     * @param configName 配置名称
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * 获取最大值
     * @return 最大值
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    /**
     * 设置最大值
     * @param maxValue 最大值
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 获取当前值
     * @return 当前值
     */
    public Integer getCurrentValue() {
        return currentValue;
    }

    /**
     * 设置当前值
     * @param currentValue 当前值
     */
    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

}