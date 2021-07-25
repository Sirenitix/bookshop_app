package org.example.config;

import org.example.services.IdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppContextConfig {

    @Bean
    public IdProvider idProvider(){
        return new IdProvider();
    }

}
