package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import com.github.mybatisq.Column;
import com.github.mybatisq.Join;
import com.github.mybatisq.DeleteQuery;
import com.github.mybatisq.Query;
import com.github.mybatisq.Table;

public class ElasticDispatcherWorkerTable extends Table {

    private ElasticDispatcherWorkerTable() {
        super("elastic_dispatcher_worker", "e1");
    }

    public static final ElasticDispatcherWorkerTable elastic_dispatcher_worker = new ElasticDispatcherWorkerTable();

    public Query<ElasticDispatcherWorkerTable> query() {
        return new Query<>(elastic_dispatcher_worker);
    }

    public DeleteQuery<ElasticDispatcherWorkerTable> deleteQuery() {
        return new DeleteQuery<>(elastic_dispatcher_worker);
    }

    public <T extends Table> Join<ElasticDispatcherWorkerTable, T> inner(T table) {
        return new Join<>("inner", this, table);
    }

    /**
     * 工作者id
     */
    public Column<ElasticDispatcherWorkerTable, Integer> worker_id = new Column<>("worker_id");

    /**
     * 工作者名称
     */
    public Column<ElasticDispatcherWorkerTable, String> worker_name = new Column<>("worker_name");

    /**
     * 最大工作线程数
     */
    public Column<ElasticDispatcherWorkerTable, Integer> max_threads = new Column<>("max_threads");

    /**
     * 当前工作线程数
     */
    public Column<ElasticDispatcherWorkerTable, Integer> current_threads = new Column<>("current_threads");
}