package org.ghazoui.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class CorsConfig {


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                // 🔒 Configuration CORS et CSRF
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {}) // ✅ Activé (utilise le bean CorsWebFilter)

                // 🔐 Configuration des routes
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/**").permitAll()
                        // Autoriser les requêtes OPTIONS (preflight CORS) sans authentification
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyExchange().authenticated()
                )

                // 🔑 Authentification via JWT (corrigé)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }

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
