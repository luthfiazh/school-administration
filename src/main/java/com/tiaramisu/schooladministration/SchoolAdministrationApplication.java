package com.tiaramisu.schooladministration;

import com.tiaramisu.schooladministration.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({DataSourceConfig.class})
@ComponentScan(basePackages = {"com.tiaramisu.schooladministration.service"})
@SpringBootApplication
@EnableAutoConfiguration
public class SchoolAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolAdministrationApplication.class, args);
    }

}
