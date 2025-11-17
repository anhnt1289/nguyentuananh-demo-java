package com.main.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Configuration
@PropertySource({"classpath:base-cfg.properties"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "baseProjectEntityManagerFactory",
        transactionManagerRef = "baseProjectTransactionManager",
        basePackages = {"com.base.common"})
@EnableJpaAuditing
public class PersistenceConfig {
    
    @Autowired
    private Environment env;

    @Bean(name = "baseProjectDataSource")
    @ConfigurationProperties(prefix = "base.project.spring.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("base.project.spring.datasource.url"));
        dataSource.setUsername(env.getProperty("base.project.spring.datasource.username"));
        dataSource.setPassword(env.getProperty("base.project.spring.datasource.password"));

        return dataSource;
    }

    @Bean(name = "baseProjectEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean baseProjectEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                              @Qualifier("baseProjectDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.base.common")
                .build();
    }

    @Primary
    @Bean(name = "baseProjectTransactionManager")
    public PlatformTransactionManager baseProjectTransactionManager(
            @Qualifier("baseProjectEntityManagerFactory") EntityManagerFactory
                    entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
