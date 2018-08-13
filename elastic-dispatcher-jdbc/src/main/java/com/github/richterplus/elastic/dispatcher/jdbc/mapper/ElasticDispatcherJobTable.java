package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import com.github.mybatisq.Column;
import com.github.mybatisq.Join;
import com.github.mybatisq.DeleteQuery;
import com.github.mybatisq.Query;
import com.github.mybatisq.Table;

public class ElasticDispatcherJobTable extends Table {

    private ElasticDispatcherJobTable() {
        super("elastic_dispatcher_job", "e0");
    }

    public static final ElasticDispatcherJobTable elastic_dispatcher_job = new ElasticDispatcherJobTable();

    public Query<ElasticDispatcherJobTable> query() {
        return new Query<>(elastic_dispatcher_job);
    }

    public DeleteQuery<ElasticDispatcherJobTable> deleteQuery() {
        return new DeleteQuery<>(elastic_dispatcher_job);
    }

    public <T extends Table> Join<ElasticDispatcherJobTable, T> inner(T table) {
        return new Join<>("inner", this, table);
    }

    /**
     * 任务id
     */
    public Column<ElasticDispatcherJobTable, Integer> job_id = new Column<>("job_id");

    /**
     * 任务uuid
     */
    public Column<ElasticDispatcherJobTable, String> job_uuid = new Column<>("job_uuid");

    /**
     * 外部任务编号
     */
    public Column<ElasticDispatcherJobTable, String> job_no = new Column<>("job_no");

    /**
     * 工作者id
     */
    public Column<ElasticDispatcherJobTable, Integer> worker_id = new Column<>("worker_id");

    /**
     * 任务信息
     */
    public Column<ElasticDispatcherJobTable, String> job_info = new Column<>("job_info");

    /**
     * 任务状态（-1：取消，0：未开始，1：进行中，2：成功，-2：失败）
     */
    public Column<ElasticDispatcherJobTable, Integer> job_state = new Column<>("job_state");

    /**
     * 期望开始日期
     */
    public Column<ElasticDispatcherJobTable, java.util.Date> expected_start = new Column<>("expected_start");

    /**
     * 可接受的开始日期
     */
    public Column<ElasticDispatcherJobTable, java.util.Date> acceptable_start = new Column<>("acceptable_start");

    /**
     * 超时时间
     */
    public Column<ElasticDispatcherJobTable, java.util.Date> expire_date = new Column<>("expire_date");

    /**
     * 实际开始时间
     */
    public Column<ElasticDispatcherJobTable, java.util.Date> actual_start = new Column<>("actual_start");

    /**
     * 实际结束时间
     */
    public Column<ElasticDispatcherJobTable, java.util.Date> actual_end = new Column<>("actual_end");

    /**
     * 最大重试次数
     */
    public Column<ElasticDispatcherJobTable, Integer> max_retry = new Column<>("max_retry");

    /**
     * 重试次数
     */
    public Column<ElasticDispatcherJobTable, Integer> retry_times = new Column<>("retry_times");

    /**
     * 失败原因
     */
    public Column<ElasticDispatcherJobTable, String> fail_reason = new Column<>("fail_reason");

    /**
     * 任务创建日期
     */
    public Column<ElasticDispatcherJobTable, java.util.Date> create_date = new Column<>("create_date");
}