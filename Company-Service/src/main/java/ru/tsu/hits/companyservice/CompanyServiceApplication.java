package ru.tsu.hits.companyservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@OpenAPIDefinition
@EnableMethodSecurity
@ComponentScan(basePackages = {"ru.tsu.hits.internship.security", "ru.tsu.hits.internship.common", "ru.tsu.hits.companyservice"})
public class CompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }

}
