package com.ssk.shardingjdbc.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractBaseDruidConfig {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int DRUID_INITIAL_SIZE = 10;
    private static final int DRUID_MIN_IDLE = 0;
    private static final int DRUID_MAX_ACTIVE = 100;
    private static final long DRUID_MAX_WAIT = 10000;
    private static final long DRUID_TIME_BETWEEN_EVICTION_RUNS_MILLS = 60000;
    private static final long DRUID_MIN_EVICTABLE_IDLE_TIME_MILLS = 3600000;
    private static final int DRUID_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = 100;


    public DruidDataSource setupDataSource(DruidDataSource dataSource) {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setDbType("mysql");
        if(dataSource.getInitialSize() == DruidDataSource.DEFAULT_INITIAL_SIZE) {
            dataSource.setInitialSize(DRUID_INITIAL_SIZE);
        }
        if(dataSource.getMinIdle() == DruidDataSource.DEFAULT_MIN_IDLE) {
            dataSource.setMinIdle(DRUID_MIN_IDLE);
        }
        if(dataSource.getMaxActive() == DruidDataSource.DEFAULT_MAX_ACTIVE_SIZE) {
            dataSource.setMaxActive(DRUID_MAX_ACTIVE);
        }
        if(dataSource.getMaxWait() == DruidDataSource.DEFAULT_MAX_WAIT) {
            dataSource.setMaxWait(DRUID_MAX_WAIT);
        }
        dataSource.setTimeBetweenEvictionRunsMillis(DRUID_TIME_BETWEEN_EVICTION_RUNS_MILLS);
        dataSource.setMinEvictableIdleTimeMillis(DRUID_MIN_EVICTABLE_IDLE_TIME_MILLS);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(DRUID_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE);
        try {
            dataSource.setFilters("stat,wall,log4j");
        } catch (SQLException e) {
            logger.error("Druid sets filter failed", e);
        }
        Properties properties = new Properties();
        properties.setProperty("druid.stat.mergeSql", "true");
        properties.setProperty("druid.stat.slowSqlMillis", "1000");
        dataSource.setConnectProperties(properties);
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
