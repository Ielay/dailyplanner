package com.dailyplanner.task.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

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
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        String prop = env.getProperty("liquibase.changeLogFile");
        System.out.println(prop.equals("classpath:liquibase/changelog-master.xml"));

        liquibase.setChangeLog("classpath:liquibase/changelog-master.xml");
        liquibase.setDataSource(dataSource);

        return liquibase;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setUsername(env.getProperty("ds.username"));
        ds.setPassword(env.getProperty("ds.password"));
        ds.setDriverClassName(env.getProperty("ds.driver"));
        ds.setUrl(env.getProperty("ds.url"));

        return ds;
    }
}
