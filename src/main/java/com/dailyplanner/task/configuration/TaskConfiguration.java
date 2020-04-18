package com.dailyplanner.task.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author lelay
 * @since 18.04.20
 */
@Configuration
@PropertySource("classpath:liquibase/liquibase.properties")
public class TaskConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public String test() {
        return "aaaaaaaaaa";
    }
}
