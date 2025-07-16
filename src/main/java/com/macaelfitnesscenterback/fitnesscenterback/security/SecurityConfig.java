package com.macaelfitnesscenterback.fitnesscenterback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.Customizer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/usuarios/login",
                                "/api/usuarios/registrar",
                                "/api/usuarios/solicitar-restablecimiento",
                                "/api/usuarios/restablecer-contrasena",
                                "/api/planes/**",
                                "/api/rutinas/**",
                                "/api/noticias/**",
                                "/api/noticias",
                                "/api/favoritos/**",
                                "/api/subirNoticias",
                                "/api/upload-imagen",
                                "/images/**")
                        .permitAll() // Permitir acceso sin autenticación
                        .anyRequest().authenticated() // Cualquier otro endpoint requiere autenticación
                );

        return http.build(); // Devuelve la cadena de filtros configurada
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173")); // Origen permitido (frontend)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite envío de cookies/tokens con las solicitudes
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Aplica la configuración a rutas bajo /api/**

        return source;
    }
}
