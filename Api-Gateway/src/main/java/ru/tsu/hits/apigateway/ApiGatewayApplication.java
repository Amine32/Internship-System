package ru.tsu.hits.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/company/**")
                        .uri("http://localhost:8081/"))
                .route(p -> p
                        .path("/internship/**")
                        .uri("http://localhost:8082/"))
                .route(p -> p
                        .path("/user/**")
                        .uri("http://localhost:8083/"))
                .build();
    }
}
