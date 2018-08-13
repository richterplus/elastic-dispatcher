package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JobMapper {

    int updateJobStateByJobState(@Param("job_id") int jobId, @Param("new_job_state") int newJobState, @Param("old_job_state") int oldJobState);
}
