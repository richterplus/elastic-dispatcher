package com.github.richterplus.elastic.dispatcher.jdbc.repository;

import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherWorker;

import java.util.List;

public interface ElasticDispatcherWorkerRepository {

    ElasticDispatcherWorker getByWorkerId(Integer workerId);

    ElasticDispatcherWorker getByWorkerName(String workerName);

    List<ElasticDispatcherWorker> listByMaxThreadsEqCurrentThreads();

    void create(ElasticDispatcherWorker worker);

    void update(ElasticDispatcherWorker worker);

    void delete(Integer workerId);

    boolean incrementCurrentThreads(int workerId);

    boolean decrementCurrentThreads(int workerId);

    List<ElasticDispatcherWorker> listByMinCurrentThreads(int minCurrentThreads);
}
