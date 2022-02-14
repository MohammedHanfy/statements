package com.mah.statements.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.mah.statements.repository"})
public class DataSourceConfig {

    private final PropertiesConfig propertiesConfig;

    public DataSourceConfig(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    @Bean
    public DataSource dataSource() {

        var dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.driverClassName(propertiesConfig.getDataBaseProperties().getDriverClassName());
        dataSourceBuilder.url(propertiesConfig.getDataBaseProperties().getUrl());
        dataSourceBuilder.username(propertiesConfig.getDataBaseProperties().getUsername());
        dataSourceBuilder.password(propertiesConfig.getDataBaseProperties().getPassword());

        return dataSourceBuilder.build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.mah.statements.entity");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(properties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        var transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties properties() {

        var properties = new Properties();

        properties.put("hibernate.dialect", propertiesConfig.getHibernateProperties().getDialect());
        properties.put("hibernate.hbm2ddl.auto", propertiesConfig.getHibernateProperties().getHbm2ddlAuto());
        properties.put("hibernate.ddl-auto", propertiesConfig.getHibernateProperties().getDdlAuto());
        properties.put("hibernate.show_sql", propertiesConfig.getHibernateProperties().getShowSql());
        properties.put("hibernate.format_sql", propertiesConfig.getHibernateProperties().getFormatSql());
        return properties;
    }
}