package com.example.demo;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {

    @Autowired
    private org.springframework.core.env.Environment env;

    @Bean
    public DataSource dataSource() throws URISyntaxException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        // Driver
        try {
            dataSource.setDriverClass(env.getRequiredProperty("datasource.driver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        // DataSource / Get environment variables
        String jdbcUrl = env.getRequiredProperty("spring.datasource.url");
        dataSource.setJdbcUrl(jdbcUrl);
        URI databaseUrl = new URI(env.getRequiredProperty("DATABASE_URL"));
        dataSource.setUser(databaseUrl.getUserInfo().split(":")[0]);
        dataSource.setPassword(databaseUrl.getUserInfo().split(":")[1]);
        dataSource.setMinPoolSize(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.min_size")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.max_size")));
        dataSource.setAcquireIncrement(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.acquire_increment")));
        dataSource.setMaxIdleTime(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.max_idle_time")));
        dataSource.setMaxStatements(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.max_statements")));
        dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.idle_test_period")));
        //dataSource.setUnreturnedConnectionTimeout(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.unreturned_connection_timeout")));
        //dataSource.setDebugUnreturnedConnectionStackTraces(Boolean.parseBoolean(env.getRequiredProperty("hibernate.c3p0.debug_unreturned_connection_stack_traces")));
        dataSource.setMaxConnectionAge(Integer.parseInt(env.getRequiredProperty("hibernate.c3p0.max_connection_age")));

        return dataSource;
    }

    /**
     * Initialize hibernate properties
     *
     * @return Properties
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.put(AvailableSettings.DEFAULT_SCHEMA, env.getRequiredProperty("hibernate.defaultschema"));
        properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        properties.put(AvailableSettings.DEFAULT_SCHEMA, ""); // !!! IMPORTANT NO SCHEMA !!!

        return properties;
    }

//    @Bean("sessionFactory")
//    public LocalSessionFactoryBean getSessionFactory() throws URISyntaxException {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(getDataSource());
//        sessionFactory.setPackagesToScan(new String[] { "de.defactox.loyaltyengine.database.*" });
//        sessionFactory.setHibernateProperties(getHibernateProperties());
//        return sessionFactory;
//    }

//    @Bean("transactionManager")
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory);
//        return txManager;
//    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
}