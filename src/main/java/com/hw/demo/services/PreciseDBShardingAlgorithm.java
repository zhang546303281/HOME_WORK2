package com.hw.demo.services;

import com.hw.demo.utils.RouteHashShardDefinition;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class PreciseDBShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(PreciseDBShardingAlgorithm.class);

    /**
     * 数据源分片
     * @return String
     */
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<String> preciseShardingValue) {
        String shardDbIndex = RouteHashShardDefinition.getDbShard(preciseShardingValue.getValue());
        LOGGER.info("data source doSharding preciseShardingValue:{},shardDbIndex:{}", preciseShardingValue, shardDbIndex);
        return shardDbIndex;
    }
}
