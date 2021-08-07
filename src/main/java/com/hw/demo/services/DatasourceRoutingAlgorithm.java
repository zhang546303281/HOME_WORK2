package com.hw.demo.services;

import com.hw.demo.enums.Datasource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public class DatasourceRoutingAlgorithm implements HintShardingAlgorithm<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceRoutingAlgorithm.class);

    private static final String WRITE = "write";

    private static final String READ = "read";


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> hintShardingValue) {
        Collection<String> result = new HashSet<>();
        for (String value : hintShardingValue.getValues()) {
            if (StringUtils.equals(Datasource.READ_DATASOURCE.getValue(), value)) {
                if (availableTargetNames.contains(READ)) {
                    result.add(READ);
                }
            } else if (StringUtils.equals(Datasource.WRITE_DATASOURCE.getValue(), value)) {
                if (availableTargetNames.contains(WRITE)) {
                    result.add(WRITE);
                }
            } else {
                throw new RuntimeException("unknown datasource");
            }
        }
        LOGGER.info("availableTargetNames:{},hintShardingValue:{},dataSource:{}", availableTargetNames, hintShardingValue, result);
        return result;
    }
}
