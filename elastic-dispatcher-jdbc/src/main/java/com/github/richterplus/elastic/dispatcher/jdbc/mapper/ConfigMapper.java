package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ConfigMapper {

    int incrementCurrentValueByConfigName(@Param("config_name") String configName);

    int decrementCurrentValueByConfigName(@Param("config_name") String configName);
}
