package com.github.richterplus.elastic.dispatcher.jdbc.repository;

import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherJob;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ElasticDispatcherJobRepository {

    ElasticDispatcherJob getByUuid(String jobUuid);

    ElasticDispatcherJob getByJobNo(String jobNo);

    List<ElasticDispatcherJob> listAndSortByAcceptableStartAsc(Integer jobState, Date maxExpectedStart, Collection<Integer> excludeWorkerIds, int limit);

    List<ElasticDispatcherJob> listByJobStateAndMaxExpireDate(Integer jobState, Date maxExpireDate);

    void create(ElasticDispatcherJob job);

    void update(ElasticDispatcherJob job);

    boolean updateJobState(int jobId, int newState, int oldState);
}
