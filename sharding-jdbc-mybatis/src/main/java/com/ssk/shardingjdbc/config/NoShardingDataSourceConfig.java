package com.ssk.shardingjdbc.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-17-上午 11:30
 */
@Configuration
@MapperScan(basePackages = "com.ssk.shardingjdbc.mapper.nosharding", sqlSessionTemplateRef = "noshardingSqlSessionTemplate")
public class NoShardingDataSourceConfig {

    @Value("${spring.shardingsphere.datasource.shard_order_0.username}")
    private String username0;
    @Value("${spring.shardingsphere.datasource.shard_order_0.url}")
    private String url0;
    @Value("${spring.shardingsphere.datasource.shard_order_0.password}")
    private String password0;

    @Bean(name = "noshardingDataSource")
    public DataSource testDataSource() {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(url0);
        result.setUsername(username0);
        result.setPassword(password0);
        return result;
    }

    /**
     * 获取sqlSessionFactory实例
     *
     * @param shardingDataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "noshardingSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("noshardingDataSource") DataSource shardingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(shardingDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/nosharding/*.xml"));
        return bean.getObject();
    }


    @Bean(name = "noshardingSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("noshardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
