package com.github.richterplus.elastic.dispatcher.jdbc.repository;

import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherConfig;

public interface ElasticDispatcherConfigRepository {

    ElasticDispatcherConfig getByConfigName(String configName);

    void update(ElasticDispatcherConfig config);

    boolean incrementCurrentValue(String configName);

    boolean decrementCurrentValue(String configName);
}
