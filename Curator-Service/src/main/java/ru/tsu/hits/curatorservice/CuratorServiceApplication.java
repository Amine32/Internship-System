package ru.tsu.hits.curatorservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OpenAPIDefinition
@ComponentScan(basePackages = {"ru.tsu.hits.internship.security", "ru.tsu.hits.internship.common", "ru.tsu.hits.curatorservice"})
public class CuratorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuratorServiceApplication.class, args);
    }

}
