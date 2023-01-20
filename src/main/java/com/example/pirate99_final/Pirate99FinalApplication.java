package com.example.pirate99_final;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableBatchProcessing
@EnableJpaAuditing
@EnableCaching
public class Pirate99FinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pirate99FinalApplication.class, args);
    }
}
