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
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service
                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8081"))

                // Application Service
                .route("application-service", r -> r
                        .path("/application-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8082"))

                // Company Service
                .route("company-service", r -> r
                        .path("/company-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8083"))

                // Stack Service
                .route("stack-service", r -> r
                        .path("/stack-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8084"))

                // Curator Service
                .route("curator-service", r -> r
                        .path("/curator-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8085"))

                // Season Service
                .route("season-service", r -> r
                        .path("/season-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8086"))

                // Practice Service
                .route("practice-service", r -> r
                        .path("/practice-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8087"))

                // Document Service
                .route("document-service", r -> r
                        .path("/document-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8088"))

                // Swagger UI routes for each service
                .route("user-service-swagger", r -> r
                        .path("/user-service/swagger-ui/**", "/user-service/v3/api-docs/**")
                        .uri("http://localhost:8081"))

                .route("application-service-swagger", r -> r
                        .path("/application-service/swagger-ui/**", "/application-service/v3/api-docs/**")
                        .uri("http://localhost:8082"))

                .route("company-service-swagger", r -> r
                        .path("/company-service/swagger-ui/**", "/company-service/v3/api-docs/**")
                        .uri("http://localhost:8083"))

                .route("stack-service-swagger", r -> r
                        .path("/stack-service/swagger-ui/**", "/stack-service/v3/api-docs/**")
                        .uri("http://localhost:8084"))

                .route("curator-service-swagger", r -> r
                        .path("/curator-service/swagger-ui/**", "/curator-service/v3/api-docs/**")
                        .uri("http://localhost:8085"))

                .route("season-service-swagger", r -> r
                        .path("/season-service/swagger-ui/**", "/season-service/v3/api-docs/**")
                        .uri("http://localhost:8086"))

                .route("practice-service-swagger", r -> r
                        .path("/practice-service/swagger-ui/**", "/practice-service/v3/api-docs/**")
                        .uri("http://localhost:8087"))

                .route("document-service-swagger", r -> r
                        .path("/document-service/swagger-ui/**", "/document-service/v3/api-docs/**")
                        .uri("http://localhost:8088"))

                .build();
    }
}