package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.mybatisq.DeleteQuery;
import com.github.mybatisq.Query;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherWorker;

@Mapper
public interface ElasticDispatcherWorkerMapper {

    int count(Query<ElasticDispatcherWorkerTable> query);

    List<ElasticDispatcherWorker> select(Query<ElasticDispatcherWorkerTable> query);

    int insert(ElasticDispatcherWorker elasticDispatcherWorker);

    int batchInsert(@Param("list") Collection<ElasticDispatcherWorker> elasticDispatcherWorker);

    int update(ElasticDispatcherWorker elasticDispatcherWorker);

    int batchUpdate(@Param("list") Collection<ElasticDispatcherWorker> elasticDispatcherWorker);

    int delete(@Param("workerId") Integer workerId);

    int batchDelete(@Param("workerIdList") Collection<Integer> workerIdList);

    int deleteByQuery(DeleteQuery<ElasticDispatcherWorkerTable> query);

}