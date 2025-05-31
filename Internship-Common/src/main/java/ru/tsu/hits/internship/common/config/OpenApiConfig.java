package ru.tsu.hits.internship.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Common OpenAPI configuration for all services.
 * This class can be extended or used directly by services to configure their OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components()
                                    .addSecuritySchemes("JWT", new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")
                                            .in(SecurityScheme.In.HEADER)
                                            .name("Authorization")
                                            .description("Please remove 'Bearer ' prefix from the token")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"));
    }
}
