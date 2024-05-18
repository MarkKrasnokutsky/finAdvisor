package com.complex.finAdvisor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080")
                        )
                )
                .info(
                        new Info().title("Test API for Signals")
                );
    }
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/user/**", "/auth/**", "/secured/**")
                .build();
    }
    @Bean
    public GroupedOpenApi signalApi() {
        return GroupedOpenApi.builder()
                .group("singal")
                .pathsToMatch("/signals/**")
                .build();
    }
    @Bean
    public GroupedOpenApi instrumentApi() {
        return GroupedOpenApi.builder()
                .group("instrument")
                .pathsToMatch("/instruments/**")
                .build();
    }
    @Bean
    public GroupedOpenApi tariffApi() {
        return GroupedOpenApi.builder()
                .group("tariff")
                .pathsToMatch("/tariff/**")
                .build();
    }
}
