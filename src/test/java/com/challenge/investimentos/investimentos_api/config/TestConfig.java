package com.challenge.investimentos.investimentos_api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@TestConfiguration
@ActiveProfiles("test")
@ComponentScan(basePackages = "com.challenge.investimentos.investimentos_api")
@EnableWebSecurity
@Import({SecurityConfig.class})
public class TestConfig {
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .driverClassName("oracle.jdbc.OracleDriver")
            .url("jdbc:oracle:thin:@//oracle.fiap.com.br:1521/orcl")
            .username("RM98690") 
            .password("041003")  
            .build();
    }
}