package com.github.richterplus.elastic.dispatcher.jdbc.mapper;

import com.github.mybatisq.Column;
import com.github.mybatisq.Join;
import com.github.mybatisq.DeleteQuery;
import com.github.mybatisq.Query;
import com.github.mybatisq.Table;

public class ElasticDispatcherConfigTable extends Table {

    private ElasticDispatcherConfigTable() {
        super("elastic_dispatcher_config", "e");
    }

    public static final ElasticDispatcherConfigTable elastic_dispatcher_config = new ElasticDispatcherConfigTable();

    public Query<ElasticDispatcherConfigTable> query() {
        return new Query<>(elastic_dispatcher_config);
    }

    public DeleteQuery<ElasticDispatcherConfigTable> deleteQuery() {
        return new DeleteQuery<>(elastic_dispatcher_config);
    }

    public <T extends Table> Join<ElasticDispatcherConfigTable, T> inner(T table) {
        return new Join<>("inner", this, table);
    }

    /**
     * 配置id
     */
    public Column<ElasticDispatcherConfigTable, Integer> config_id = new Column<>("config_id");

    /**
     * 配置名称
     */
    public Column<ElasticDispatcherConfigTable, String> config_name = new Column<>("config_name");

    /**
     * 最大值
     */
    public Column<ElasticDispatcherConfigTable, Integer> max_value = new Column<>("max_value");

    /**
     * 当前值
     */
    public Column<ElasticDispatcherConfigTable, Integer> current_value = new Column<>("current_value");
}