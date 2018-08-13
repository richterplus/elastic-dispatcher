package com.github.richterplus.elastic.dispatcher.jdbc.repository.impl;

import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherWorker;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ElasticDispatcherWorkerMapper;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ElasticDispatcherWorkerTable;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.WorkerMapper;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class ElasticDispatcherWorkerRepositoryImpl implements ElasticDispatcherWorkerRepository {

    @Autowired
    private ElasticDispatcherWorkerMapper elasticDispatcherWorkerMapper;

    @Autowired
    private WorkerMapper workerMapper;

    @Override
    public ElasticDispatcherWorker getByWorkerId(Integer workerId) {
        if (workerId == null) return null;
        ElasticDispatcherWorkerTable w = ElasticDispatcherWorkerTable.elastic_dispatcher_worker;
        List<ElasticDispatcherWorker> workers = elasticDispatcherWorkerMapper.select(w.query().where(w.worker_id.eq(workerId)).limit(1));
        return workers.size() == 0 ? null : workers.get(0);
    }

    @Override
    public ElasticDispatcherWorker getByWorkerName(String workerName) {
        if (workerName == null) return null;
        ElasticDispatcherWorkerTable w = ElasticDispatcherWorkerTable.elastic_dispatcher_worker;
        List<ElasticDispatcherWorker> workers = elasticDispatcherWorkerMapper.select(w.query().where(w.worker_name.eq(workerName)).limit(1));
        return workers.size() == 0 ? null : workers.get(0);
    }

    @Override
    public List<ElasticDispatcherWorker> listByMaxThreadsEqCurrentThreads() {
        ElasticDispatcherWorkerTable w = ElasticDispatcherWorkerTable.elastic_dispatcher_worker;
        return elasticDispatcherWorkerMapper.select(w.query().where(w.max_threads.eqAnother(w.current_threads)));
    }

    @Override
    public void create(ElasticDispatcherWorker worker) {
        elasticDispatcherWorkerMapper.insert(worker);
    }

    @Override
    public void update(ElasticDispatcherWorker worker) {
        elasticDispatcherWorkerMapper.update(worker);
    }

    @Override
    public void delete(Integer workerId) {
        if (workerId != null) {
            elasticDispatcherWorkerMapper.delete(workerId);
        }
    }

    @Override
    public boolean incrementCurrentThreads(int workerId) {
        return workerMapper.incrementCurrentThreads(workerId) > 0;
    }

    @Override
    public boolean decrementCurrentThreads(int workerId) {
        return workerMapper.decrementCurrentThreads(workerId) > 0;
    }

    @Override
    public List<ElasticDispatcherWorker> listByMinCurrentThreads(int minCurrentThreads) {
        ElasticDispatcherWorkerTable w = ElasticDispatcherWorkerTable.elastic_dispatcher_worker;
        return elasticDispatcherWorkerMapper.select(w.query().where(w.current_threads.ge(minCurrentThreads)));
    }
}
