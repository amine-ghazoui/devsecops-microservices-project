package org.ghazoui.commandeservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // ❌ Pas de configuration CORS ici - le gateway gère CORS
                // Le gateway ajoute déjà les en-têtes CORS, pas besoin de les dupliquer
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        // Autoriser les requêtes OPTIONS (preflight) sans authentification
                        // Le gateway gère les en-têtes CORS, mais on doit autoriser OPTIONS ici
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {}) // lambda vide, configure si besoin
                );

        return http.build();
    }
}