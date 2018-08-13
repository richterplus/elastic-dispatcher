package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.mybatisq.DeleteQuery;
import com.github.mybatisq.Query;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherJob;

@Mapper
public interface ElasticDispatcherJobMapper {

    int count(Query<ElasticDispatcherJobTable> query);

    List<ElasticDispatcherJob> select(Query<ElasticDispatcherJobTable> query);

    int insert(ElasticDispatcherJob elasticDispatcherJob);

    int batchInsert(@Param("list") Collection<ElasticDispatcherJob> elasticDispatcherJob);

    int update(ElasticDispatcherJob elasticDispatcherJob);

    int batchUpdate(@Param("list") Collection<ElasticDispatcherJob> elasticDispatcherJob);

    int delete(@Param("jobId") Integer jobId);

    int batchDelete(@Param("jobIdList") Collection<Integer> jobIdList);

    int deleteByQuery(DeleteQuery<ElasticDispatcherJobTable> query);

}