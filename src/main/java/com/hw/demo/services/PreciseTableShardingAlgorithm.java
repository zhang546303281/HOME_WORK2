package com.hw.demo.services;

import com.google.common.collect.Range;
import com.hw.demo.utils.RouteHashShardDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(PreciseTableShardingAlgorithm.class);

    private final static Map<String, String> TABLE_REL = new HashMap<>();

    static {
        //逻辑表名|真实表名
        TABLE_REL.put("user", "t_user_base_info");
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        String shardDbIndex = RouteHashShardDefinition.getTbShard(preciseShardingValue.getValue());
        LOGGER.info("table source doSharding preciseShardingValue:{},shardDbIndex:{}", preciseShardingValue, shardDbIndex);
        String targetTableName = StringUtils.join(getTrueName(preciseShardingValue.getLogicTableName()), shardDbIndex);
        LOGGER.info("table source doSharding targetTableName:{}", targetTableName);
        return targetTableName;
    }

    private String getTrueName(String logicName) {
        for (Map.Entry<String, String> entry : TABLE_REL.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.equals(key, logicName)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("rel not exist!");
    }
}
