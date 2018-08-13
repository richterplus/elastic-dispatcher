package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WorkerMapper {

    int incrementCurrentThreads(@Param("worker_id") int worker_id);

    int decrementCurrentThreads(@Param("worker_id") int worker_id);
}
