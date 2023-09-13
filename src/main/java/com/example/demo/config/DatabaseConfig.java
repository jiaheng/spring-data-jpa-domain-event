package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "datasource")
@EntityScan("com.example.demo.entity")
@EnableJpaRepositories("com.example.demo.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    private Properties hikari;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(hikari);
        return new HikariDataSource(config);
    }

    @Bean
    public HibernatePropertiesCustomizer jsonFormatHibernateCustomizer(ObjectMapper objectMapper) {
        return hibernateProperties ->
                hibernateProperties.put(
                        AvailableSettings.JSON_FORMAT_MAPPER,
                        new JacksonJsonFormatMapper(objectMapper));
    }

    public void setHikari(final Properties hikari) {
        this.hikari = hikari;
    }
}
