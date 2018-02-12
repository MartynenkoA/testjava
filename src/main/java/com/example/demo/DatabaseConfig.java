package com.example.demo;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Contains database configurations.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    @Autowired
    private org.springframework.core.env.Environment  env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    /**
     * DataSource definition for database connection. Settings are read from
     * the application.properties file (using the env object).
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("datasource.driver"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    /**
     * Declare the JPA entity manager factory.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);

        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan(
                env.getProperty("entitymanager.packagesToScan"));

        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put(
                "hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        additionalProperties.put(
                "hibernate.show_sql",
                env.getProperty("hibernate.show_sql"));
        additionalProperties.put(
                "hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        // Secondary Cache
        additionalProperties.put(Environment.USE_SECOND_LEVEL_CACHE, true);
        additionalProperties.put(Environment.USE_QUERY_CACHE, true);
        additionalProperties.put(Environment.CACHE_REGION_FACTORY, org.hibernate.cache.redis.hibernate52.SingletonRedisRegionFactory.class.getName());
        additionalProperties.put(Environment.CACHE_REGION_PREFIX, "hibernate");

// Optional setting for second level cache statistics
        additionalProperties.setProperty(Environment.GENERATE_STATISTICS, "true");
        additionalProperties.setProperty(Environment.USE_STRUCTURED_CACHE, "true");

// Configuration for Redis that used by hibernate
        additionalProperties.put(Environment.CACHE_PROVIDER_CONFIG, "hibernate-redis.properties");
        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    /**
     * Declare the transaction manager.
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
