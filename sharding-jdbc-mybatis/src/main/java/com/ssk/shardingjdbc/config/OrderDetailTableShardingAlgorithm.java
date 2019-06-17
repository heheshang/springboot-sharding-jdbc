package com.ssk.shardingjdbc.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-17-上午 11:29
 */
@Data
@Slf4j
@Service("orderDetailTableShardingAlgorithm")
public class OrderDetailTableShardingAlgorithm extends CommonTableShardingAlgorithm  {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        return super.doSharding(collection, preciseShardingValue);
    }
}
