package com.niit.bej.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/toDo/**").uri("http://localhost:8096/"))
                .route(p -> p.path("/user/**").uri("http://localhost:8085/"))
                .route(p -> p.path("/notificationController/**").uri("http://localhost:8089/")).build();

    }
}
