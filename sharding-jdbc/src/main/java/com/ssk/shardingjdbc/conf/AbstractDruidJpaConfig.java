package com.ssk.shardingjdbc.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

public class AbstractDruidJpaConfig extends AbstractBaseDruidConfig {

    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DruidDataSource dataSource, String entityPackagesToScan) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(setupDataSource(dataSource));
        localContainerEntityManagerFactoryBean.setPackagesToScan(entityPackagesToScan);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.jdbc.batch_size", 500);
        properties.put("hibernate.query.substitutions", "true 1,false 0");
        properties.put("hibernate.connection.autocommit", false);
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    private HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return hibernateJpaVendorAdapter;
    }
}
