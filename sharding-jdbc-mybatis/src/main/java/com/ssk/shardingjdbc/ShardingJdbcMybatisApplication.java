package com.ssk.shardingjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class ShardingJdbcMybatisApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShardingJdbcMybatisApplication.class, args);
    }

}
