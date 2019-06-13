package com.ssk.shardingjdbc.conf;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author sunshow
 */
public abstract class AbstractBaseJpaConfig {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    abstract protected String entityPackagesToScan();

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.query.substitutions", env.getProperty("hibernate.query.substitutions"));

        String hbm2ddl = env.getProperty("hibernate.hbm2ddl.auto");
        if (StringUtils.containsAny(hbm2ddl, "validate", "update", "create", "create-drop")) {
            properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        }

        properties.setProperty("hibernate.connection.autocommit", env.getProperty("hibernate.connection.autocommit"));
        properties.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        properties.setProperty("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings", "true"));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(entityPackagesToScan());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @PreDestroy
    public void preDestroy() {
        dataSource().close();
    }

    @PostConstruct
    public void postConstruct() {
        try {
            dataSource().init();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setValidationQuery("SELECT 1");

        if(StringUtils.isNotEmpty(env.getProperty("druid.initialSize"))) {
            dataSource.setInitialSize(Integer.parseInt(env.getProperty("druid.initialSize")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.maxActive"))) {
            dataSource.setMaxActive(Integer.parseInt(env.getProperty("druid.maxActive")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.minIdle"))) {
            dataSource.setMinIdle(Integer.parseInt(env.getProperty("druid.minIdle")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.maxWait"))) {
            dataSource.setMaxWait(Integer.parseInt(env.getProperty("druid.maxWait")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.testOnBorrow"))) {
            dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("druid.testOnBorrow")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.testOnReturn"))) {
            dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("druid.testOnReturn")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.testWhileIdle"))) {
            dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("druid.testWhileIdle")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.timeBetweenEvictionRunsMillis"))) {
            dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("druid.timeBetweenEvictionRunsMillis")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.minEvictableIdleTimeMillis"))) {
            dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("druid.minEvictableIdleTimeMillis")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.removeAbandoned"))) {
            dataSource.setRemoveAbandoned(Boolean.parseBoolean(env.getProperty("druid.removeAbandoned")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.removeAbandonedTimeout"))) {
            dataSource.setRemoveAbandonedTimeout(Integer.parseInt(env.getProperty("druid.removeAbandonedTimeout")));
        }
        if(StringUtils.isNotEmpty(env.getProperty("druid.logAbandoned"))) {
            dataSource.setLogAbandoned(Boolean.parseBoolean(env.getProperty("druid.logAbandoned")));
        }
        dataSource.setProxyFilters(Arrays.asList(statFilter(), slf4jLogFilter()));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    protected StatFilter statFilter() {
        StatFilter filter = new StatFilter();
        filter.setMergeSql(StringUtils.isNotEmpty(env.getProperty("druid.filter.mergeSql")) ?
                Boolean.parseBoolean(env.getProperty("druid.filter.mergeSql")) : true);
        filter.setLogSlowSql(StringUtils.isNotEmpty(env.getProperty("druid.filter.logSlowSql")) ?
                Boolean.parseBoolean(env.getProperty("druid.filter.logSlowSql")) : true);
        filter.setSlowSqlMillis(StringUtils.isNotEmpty(env.getProperty("druid.filter.slowSqlMillis")) ?
                Long.parseLong(env.getProperty("druid.filter.slowSqlMillis")) : 1000);
        return filter;
    }

    protected Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setConnectionLogEnabled(StringUtils.isNotEmpty(env.getProperty("druid.filter.connectionLogEnabled")) ?
                Boolean.parseBoolean(env.getProperty("druid.filter.connectionLogEnabled")) : false);
        filter.setStatementLogEnabled(StringUtils.isNotEmpty(env.getProperty("druid.filter.statementLogEnabled")) ?
                Boolean.parseBoolean(env.getProperty("druid.filter.statementLogEnabled")) : false);
        filter.setResultSetLogEnabled(StringUtils.isNotEmpty(env.getProperty("druid.filter.resultSetLogEnabled")) ?
                Boolean.parseBoolean(env.getProperty("druid.filter.resultSetLogEnabled")) : true);
        filter.setStatementExecutableSqlLogEnable(StringUtils.isNotEmpty(env.getProperty("druid.filter.statementExecutableSqlLogEnable")) ?
                Boolean.parseBoolean(env.getProperty("druid.filter.statementExecutableSqlLogEnable")) : true);
        return filter;
    }

}
