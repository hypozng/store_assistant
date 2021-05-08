package com.dauivs.storeassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class StoreAssistantApplication {


    public static void main(String[] args) {
        SpringApplication.run(StoreAssistantApplication.class, args);
    }

}
