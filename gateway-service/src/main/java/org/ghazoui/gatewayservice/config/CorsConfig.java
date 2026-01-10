package org.ghazoui.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    /**
     * 🌍 Configuration CORS centralisée pour l'API Gateway
     * Toutes les requêtes du frontend Angular passent par ici
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // ✅ Autoriser les requêtes depuis le frontend Angular
        corsConfig.setAllowedOriginPatterns(List.of("http://localhost:4200"));

        // ✅ Méthodes HTTP autorisées
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // ✅ Autoriser TOUS les headers (important pour éviter les blocages)
        corsConfig.setAllowedHeaders(List.of("*"));

        // ✅ Headers exposés au client
        corsConfig.setExposedHeaders(List.of("*"));

        // ✅ Autoriser les credentials (cookies, authorization headers)
        corsConfig.setAllowCredentials(true);

        // ✅ Durée de cache pour la requête preflight (1 heure)
        corsConfig.setMaxAge(3600L);

        // ✅ Appliquer les valeurs par défaut permissives
        corsConfig.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
