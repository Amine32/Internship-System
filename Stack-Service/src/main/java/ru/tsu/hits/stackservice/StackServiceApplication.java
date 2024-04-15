package ru.tsu.hits.stackservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class StackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackServiceApplication.class, args);
    }

}
