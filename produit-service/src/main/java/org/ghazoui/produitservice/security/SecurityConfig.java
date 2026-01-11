package org.ghazoui.produitservice.security;

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
                        // ⚠️ Temporaire : Permettre l'accès GET aux keynotes pour les appels inter-services
                        // (Feign appelle directement le service sans passer par le gateway)
                        // Le gateway continue à protéger les endpoints pour le frontend
                        .requestMatchers(HttpMethod.GET, "/keynotes", "/keynotes/**").permitAll()
                        // ✅ Sécurité : toutes les autres requêtes (POST, PUT, DELETE) nécessitent une authentification JWT
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {}) // lambda vide, configure si besoin
                );

        return http.build();
    }
}