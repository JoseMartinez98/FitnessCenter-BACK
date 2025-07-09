package com.macaelfitnesscenterback.fitnesscenterback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.Customizer;

import java.util.List;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Define las políticas de acceso a los endpoints REST y la configuración CORS.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * 
     * - Se habilita el soporte para CORS.
     * - Se desactiva CSRF ya que es común en APIs REST.
     * - Se definen los endpoints públicos que no requieren autenticación.
     * - Cualquier otra solicitud debe estar autenticada.
     *
     * @param http Objeto HttpSecurity para definir reglas de seguridad.
     * @return Cadena de filtros de seguridad configurada.
     * @throws Exception En caso de errores durante la configuración.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/usuarios/login",
                                "/api/usuarios/registrar",
                                "/api/planes/**",
                                "/api/rutinas/**",
                                "/api/noticias/**",
                                "/api/favoritos/**")
                        .permitAll() // Permitir acceso sin autenticación
                        .anyRequest().authenticated() // Cualquier otro endpoint requiere autenticación
                );

        return http.build(); // Devuelve la cadena de filtros configurada
    }

    /**
     * Configura la política de CORS para los endpoints de la API.
     * 
     * - Permite solicitudes desde el frontend en http://localhost:5173.
     * - Define los métodos HTTP permitidos.
     * - Permite cualquier encabezado.
     * - Habilita el envío de credenciales (como cookies o tokens).
     * 
     * @return Fuente de configuración CORS.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173")); // Origen permitido (frontend)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite envío de cookies/tokens con las solicitudes

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Aplica la configuración a rutas bajo /api/**

        return source;
    }
}
