package org.ghazoui.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route pour produit-service - utiliser le nom exact tel qu'enregistré dans Eureka
                .route("produit-service-route", r -> r
                        .path("/api/produits/**")
                        .uri("lb://PRODUIT-SERVICE"))
                // Route pour commande-service - utiliser le nom exact tel qu'enregistré dans Eureka
                .route("commande-service-route", r -> r
                        .path("/api/commandes/**")
                        .uri("lb://COMMANDE-SERVICE"))
                .build();
    }


}
