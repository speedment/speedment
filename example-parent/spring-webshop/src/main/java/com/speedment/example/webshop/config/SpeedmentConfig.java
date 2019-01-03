package com.speedment.example.webshop.config;

import com.speedment.example.webshop.db.WebshopApplication;
import com.speedment.example.webshop.db.WebshopApplicationBuilder;
import com.speedment.example.webshop.db.products.ProductManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeedmentConfig {

    @Bean
    WebshopApplication application() {
        return new WebshopApplicationBuilder().build();
    }

    @Bean
    ProductManager productManager(WebshopApplication app) {
        return app.getOrThrow(ProductManager.class);
    }
}
