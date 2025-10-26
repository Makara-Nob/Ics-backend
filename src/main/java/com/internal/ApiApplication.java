package com.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = "com.internal")
@Slf4j
public class ApiApplication {

    public static void main(String[] args) {
        log.info("Starting Ics...");
        SpringApplication.run(ApiApplication.class, args);
        log.info("Ics started successfully");
    }
}
