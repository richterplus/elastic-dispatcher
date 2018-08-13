package com.github.richterplus.elastic.dispatcher.jdbc.repository.impl;

import com.github.mybatisq.Query;
import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherJob;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ElasticDispatcherJobMapper;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ElasticDispatcherJobTable;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.JobMapper;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
class ElasticDispatcherJobRepositoryImpl implements ElasticDispatcherJobRepository {

    @Autowired
    private ElasticDispatcherJobMapper elasticDispatcherJobMapper;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public ElasticDispatcherJob getByUuid(String jobUuid) {
        if (jobUuid == null) return null;

        ElasticDispatcherJobTable j = ElasticDispatcherJobTable.elastic_dispatcher_job;

        List<ElasticDispatcherJob> jobs = elasticDispatcherJobMapper.select(
                j.query().where(j.job_uuid.eq(jobUuid)).limit(1));

        return jobs.size() == 0 ? null : jobs.get(0);
    }

    @Override
    public ElasticDispatcherJob getByJobNo(String jobNo) {
        if (jobNo == null) return null;

        ElasticDispatcherJobTable j = ElasticDispatcherJobTable.elastic_dispatcher_job;

        List<ElasticDispatcherJob> jobs = elasticDispatcherJobMapper.select(
                j.query().where(j.job_no.eq(jobNo)).limit(1));

        return jobs.size() == 0 ? null : jobs.get(0);
    }

    @Override
    public List<ElasticDispatcherJob> listAndSortByAcceptableStartAsc(Integer jobState, Date maxExpectedStart, Collection<Integer> excludeWorkerIds, int limit) {

        if (jobState == null || maxExpectedStart == null) return Collections.emptyList();

        ElasticDispatcherJobTable j = ElasticDispatcherJobTable.elastic_dispatcher_job;
        Query<ElasticDispatcherJobTable> query = j.query()
                .where(j.job_state.eq(jobState))
                .where(j.expected_start.le(maxExpectedStart))
                .orderBy(j.acceptable_start.asc())
                .orderBy(j.expected_start.asc())
                .orderBy(j.create_date.asc())
                .limit(limit);

        if (excludeWorkerIds != null && excludeWorkerIds.size() > 0) {
            query.where(j.worker_id.notIn(excludeWorkerIds));
        }

        return elasticDispatcherJobMapper.select(query);
    }

    @Override
    public List<ElasticDispatcherJob> listByJobStateAndMaxExpireDate(Integer jobState, Date maxExpireDate) {

        if (jobState == null || maxExpireDate == null) return Collections.emptyList();

        ElasticDispatcherJobTable j = ElasticDispatcherJobTable.elastic_dispatcher_job;

        return elasticDispatcherJobMapper.select(
                j.query()
                        .where(j.job_state.eq(jobState))
                        .where(j.expire_date.le(maxExpireDate))
                        .orderBy(j.expire_date.asc()));
    }

    @Override
    public void create(ElasticDispatcherJob job) {
        elasticDispatcherJobMapper.insert(job);
    }

    @Override
    public void update(ElasticDispatcherJob job) {
        elasticDispatcherJobMapper.update(job);
    }

    @Override
    public boolean updateJobState(int jobId, int newState, int oldState) {
        return jobMapper.updateJobStateByJobState(jobId, newState, oldState) > 0;
    }
}
