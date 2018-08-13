package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.mybatisq.DeleteQuery;
import com.github.mybatisq.Query;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherConfig;

@Mapper
public interface ElasticDispatcherConfigMapper {

    int count(Query<ElasticDispatcherConfigTable> query);

    List<ElasticDispatcherConfig> select(Query<ElasticDispatcherConfigTable> query);

    int insert(ElasticDispatcherConfig elasticDispatcherConfig);

    int batchInsert(@Param("list") Collection<ElasticDispatcherConfig> elasticDispatcherConfig);

    int update(ElasticDispatcherConfig elasticDispatcherConfig);

    int batchUpdate(@Param("list") Collection<ElasticDispatcherConfig> elasticDispatcherConfig);

    int delete(@Param("configId") Integer configId);

    int batchDelete(@Param("configIdList") Collection<Integer> configIdList);

    int deleteByQuery(DeleteQuery<ElasticDispatcherConfigTable> query);

}