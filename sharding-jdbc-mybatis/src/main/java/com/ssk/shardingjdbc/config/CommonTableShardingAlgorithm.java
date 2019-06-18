package com.ssk.shardingjdbc.config;

import com.google.common.collect.Lists;
import com.ssk.shardingjdbc.mapper.nosharding.ShardConfigMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-17-上午 11:26
 */
@Data
@Slf4j
@Service("preciseModuloTableShardingAlgorithm")
public class CommonTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Autowired
    private ShardConfigMapper shardConfigMapper;


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {

        String physicsTable = null;
        physicsTable = setValue(preciseShardingValue, new ArrayList<>(collection));
        if (StringUtils.isBlank(physicsTable)) {
            log.info("----->该分片键值找不到对应的分表,默认取第一个表，分片键是={}，逻辑表是={},分片值是={}", preciseShardingValue.getColumnName(), preciseShardingValue.getLogicTableName(), preciseShardingValue.getValue());
            for (String value : collection) {
                physicsTable = value;
                break;
            }
        }
        log.info("----->该分片键值找到对应的分表，分片键是={}，逻辑表是={},分片值是={}", preciseShardingValue.getColumnName(), preciseShardingValue.getLogicTableName(), preciseShardingValue.getValue());
        return physicsTable;
    }

    /**
     * @param preciseShardingValue
     * @return
     */
    protected String setValue(PreciseShardingValue<String> preciseShardingValue, List<String> collection) {

        String v = (Long.parseLong(preciseShardingValue.getValue()) & 1) == 0 ? collection.get(1) : collection.get(0);
        return v;
    }
}
