package ru.panas.restwithoutframework.servlets.configurations;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.embedded;

@Configuration
public class SpringConfig {

    @Bean
    public EmbeddedDatabaseConnection dataSource() {
        return null;
    }
}
