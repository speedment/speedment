package com.speedment.example.webshop.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    Flyway migrate() {
        return Flyway.configure()
            .dataSource("jdbc:sqlite:webshop.db", null, null)
            .load();
    }

}
