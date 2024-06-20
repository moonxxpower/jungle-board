package com.example.jungleboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JungleBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(JungleBoardApplication.class, args);
    }
}
