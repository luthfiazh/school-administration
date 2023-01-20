package com.tiaramisu.schooladministration.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@EntityScan(basePackages = {"com.tiaramisu.schooladministration.entity"})
@Configuration
@AllArgsConstructor
public class DataSourceConfig {
    private final DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        return liquibase;
    }
}
