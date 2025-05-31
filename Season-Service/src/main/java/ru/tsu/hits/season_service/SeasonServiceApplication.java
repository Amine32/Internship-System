package ru.tsu.hits.season_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.tsu.hits.internship.security", "ru.tsu.hits.internship.common", "ru.tsu.hits.season_service"})
public class SeasonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasonServiceApplication.class, args);
    }

}
