package com.example.pirate99_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Pirate99FinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pirate99FinalApplication.class, args);
    }

}
