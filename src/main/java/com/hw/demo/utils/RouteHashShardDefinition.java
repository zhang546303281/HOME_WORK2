package com.hw.demo.utils;

import com.google.common.collect.Range;
import com.google.common.hash.Hashing;

import java.util.HashMap;
import java.util.Map;

public class RouteHashShardDefinition {

    private static Map<String, Range<Integer>> DS_SHARD_DEFINITION;

    private static Map<Integer, Range<Integer>> TB_SHARD_DEFINITION;

    static  {
        //库分片
        DS_SHARD_DEFINITION = new HashMap<>();
        DS_SHARD_DEFINITION.put("write", Range.closed(0, 511));
        DS_SHARD_DEFINITION.put("read", Range.closed(512, 1023));

        //表分片
        TB_SHARD_DEFINITION = new HashMap<>();
        TB_SHARD_DEFINITION.put(0, Range.closed(0, 63));
        TB_SHARD_DEFINITION.put(1, Range.closed(64, 127));
        TB_SHARD_DEFINITION.put(2, Range.closed(128, 191));
        TB_SHARD_DEFINITION.put(3, Range.closed(192, 255));
        TB_SHARD_DEFINITION.put(4, Range.closed(256, 319));
        TB_SHARD_DEFINITION.put(5, Range.closed(320, 383));
        TB_SHARD_DEFINITION.put(6, Range.closed(384, 447));
        TB_SHARD_DEFINITION.put(7, Range.closed(448, 511));
        TB_SHARD_DEFINITION.put(8, Range.closed(512, 575));
        TB_SHARD_DEFINITION.put(9, Range.closed(576, 639));
        TB_SHARD_DEFINITION.put(10, Range.closed(640, 703));
        TB_SHARD_DEFINITION.put(11, Range.closed(704, 767));
        TB_SHARD_DEFINITION.put(12, Range.closed(768, 831));
        TB_SHARD_DEFINITION.put(13, Range.closed(832, 895));
        TB_SHARD_DEFINITION.put(14, Range.closed(896, 959));
        TB_SHARD_DEFINITION.put(15, Range.closed(960, 1023));
    }


    /**
     * 找到对应的数据库分片
     */
    public static String getDbShard(String shardText) {
        int ratio = Hashing.consistentHash(shardText.hashCode(), 1024);
        for (Map.Entry<String, Range<Integer>> entry : DS_SHARD_DEFINITION.entrySet()) {
            Range<Integer> shardRange = entry.getValue();
            if (shardRange.contains(ratio)) {
                return String.valueOf(entry.getKey());
            }
        }

        throw new RuntimeException("unable to route datasource, pls check!");
    }

    /**
     * 找到对应的表分片
     */
    public static String getTbShard(String shardText) {
        int ratio = Hashing.consistentHash(shardText.hashCode(), 1024);
        for (Map.Entry<Integer, Range<Integer>> entry : TB_SHARD_DEFINITION.entrySet()) {
            Range<Integer> shardRange = entry.getValue();
            if (shardRange.contains(ratio)) {
                return String.valueOf(entry.getKey());
            }
        }

        throw new RuntimeException("unable to route datasource, pls check!");
    }
}
