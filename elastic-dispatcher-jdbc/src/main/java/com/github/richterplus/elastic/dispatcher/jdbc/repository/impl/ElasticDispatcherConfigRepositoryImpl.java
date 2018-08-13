package com.github.richterplus.elastic.dispatcher.jdbc.repository.impl;

import com.github.richterplus.elastic.dispatcher.jdbc.entity.ElasticDispatcherConfig;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ConfigMapper;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ElasticDispatcherConfigMapper;
import com.github.richterplus.elastic.dispatcher.jdbc.mapper.ElasticDispatcherConfigTable;
import com.github.richterplus.elastic.dispatcher.jdbc.repository.ElasticDispatcherConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class ElasticDispatcherConfigRepositoryImpl implements ElasticDispatcherConfigRepository {

    @Autowired
    private ElasticDispatcherConfigMapper elasticDispatcherConfigMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public ElasticDispatcherConfig getByConfigName(String configName) {
        if (configName == null) return null;
        ElasticDispatcherConfigTable c = ElasticDispatcherConfigTable.elastic_dispatcher_config;
        List<ElasticDispatcherConfig> configs = elasticDispatcherConfigMapper.select(c.query().where(c.config_name.eq(configName)).limit(1));
        return configs.size() == 0 ? null : configs.get(0);
    }

    @Override
    public void update(ElasticDispatcherConfig config) {
        elasticDispatcherConfigMapper.update(config);
    }

    @Override
    public boolean incrementCurrentValue(String configName) {
        return configMapper.incrementCurrentValueByConfigName(configName) > 0;
    }

    @Override
    public boolean decrementCurrentValue(String configName) {
        return configMapper.decrementCurrentValueByConfigName(configName) > 0;
    }
}
