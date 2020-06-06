package com.dailyplanner.task.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lelay
 * @since 18.04.20
 */
@Configuration
@ComponentScan(basePackages = "com.dailyplanner.task")
@PropertySource("classpath:liquibase/liquibase.properties")
public class TaskConfiguration {

    @Autowired
    private Environment env;

    @Bean("entityManagerProperties")
    public Map<String, String> entityManagerProperties() {
        try {
            Map<String, String> properties = new HashMap<>();

            properties.put("javax.persistence.jdbc.driver", env.getProperty("ds.driver"));

            if (System.getenv("DATABASE_URL") != null) {
                System.out.println("Env var is used");

                URI dbUri = new URI(System.getenv("DATABASE_URL"));

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

                properties.put("javax.persistence.jdbc.user", username);
                properties.put("javax.persistence.jdbc.password", password);
                properties.put("javax.persistence.jdbc.url", dbUrl);
            } else {
                System.out.println("Config server properties are used");

                properties.put("javax.persistence.jdbc.user", env.getProperty("ds.username"));
                properties.put("javax.persistence.jdbc.password", env.getProperty("ds.password"));
                properties.put("javax.persistence.jdbc.url", env.getProperty("ds.url"));
            }

            //hibernate orm specific params
            properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
            properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

            //hikari connection pool params
//        properties.put("hibernate.hikari.maximum_pool_size", env.getProperty("hibernate.hikari.maximum_pool_size"));
//        properties.put("hibernate.hikari.pool_name", env.getProperty("hibernate.hikari.pool_name"));
//        properties.put("hibernate.hikari.cachePrepStmts", "true");
//        properties.put("hibernate.hikari.prepStmtCacheSize", "250");
//        properties.put("hibernate.hikari.prepStmtCacheSqlLimit", "2048");

            return properties;
        } catch (Exception exc) {
            throw new BeanCreationException("Exception while creating entity namager", exc);
        }
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(@Qualifier("entityManagerProperties") Map<String, String> properties) {
        return Persistence.createEntityManagerFactory(
                "task-persistence-unit",
                properties
        );
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        var liquibase = new SpringLiquibase();

        liquibase.setChangeLog(env.getProperty("liquibase.changeLogFile"));
        liquibase.setDataSource(dataSource);

        return liquibase;
    }

    @Bean
    public DataSource dataSource() {
        try {
            DriverManagerDataSource ds = new DriverManagerDataSource();

            ds.setDriverClassName(env.getProperty("ds.driver"));

            if (System.getenv("DATABASE_URL") != null) {
                System.out.println("Env var is used");

                URI dbUri = new URI(System.getenv("DATABASE_URL"));

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

                ds.setUsername(username);
                ds.setPassword(password);
                ds.setUrl(dbUrl);
            } else {
                System.out.println("Config server properties are used");

                ds.setUsername(env.getProperty("ds.username"));
                ds.setPassword(env.getProperty("ds.password"));
                ds.setUrl(env.getProperty("ds.url"));
            }

            return ds;
        } catch (Exception exc) {
            throw new BeanCreationException("Exception while creating dataSource", exc);
        }
    }
}
