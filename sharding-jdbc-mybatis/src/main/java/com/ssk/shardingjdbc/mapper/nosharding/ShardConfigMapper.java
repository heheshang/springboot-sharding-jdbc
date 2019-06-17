package com.ssk.shardingjdbc.mapper.nosharding;

import com.ssk.shardingjdbc.model.ShardConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShardConfigMapper {

    /**
     * selectByPrimaryKey
     *
     * @param configKey
     * @return com.jay.model.ShardConfig
     */
    ShardConfig selectByPrimaryKey(String configKey);

    /**
     * selectAll
     *
     * @param keys
     * @return java.util.List<com.jay.model.ShardConfig>
     */
    List<ShardConfig> selectByKey(List<String> keys);
}
