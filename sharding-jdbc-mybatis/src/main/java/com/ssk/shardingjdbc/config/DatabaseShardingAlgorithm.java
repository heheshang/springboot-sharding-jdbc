package com.ssk.shardingjdbc.config;

import com.ssk.shardingjdbc.mapper.nosharding.ShardConfigMapper;
import com.ssk.shardingjdbc.model.ShardConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-17-上午 11:28
 */
@Data
@Slf4j
@Service("preciseModuloDatabaseShardingAlgorithm")
public class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Timestamp> {

    @Autowired
    private ShardConfigMapper shardConfigMapper;

    /**
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Timestamp> preciseShardingValue) {

        Month month = LocalDateTime.now().getMonth();
        String physicDatabase = null;
        physicDatabase = getShardConfig(physicDatabase, String.valueOf(month.getValue()));
        if (StringUtils.isBlank(physicDatabase)) {
            log.info("----->该分片键值找不到对应的分库,默认取第一个库，分片键是={}，逻辑表是={},分片值是={}", preciseShardingValue.getColumnName(), preciseShardingValue.getLogicTableName(), preciseShardingValue.getValue());
            for (String value : collection) {
                physicDatabase = value;
                break;
            }
        }
        return physicDatabase;
    }


    public String getShardConfig(String physicDatabase, String subValue) {

        ShardConfig shardConfig = shardConfigMapper.selectByPrimaryKey(subValue);
        if (shardConfig != null) {
            physicDatabase = shardConfig.getConfigValue().split(",")[0];
        }
        return physicDatabase;

    }
}
