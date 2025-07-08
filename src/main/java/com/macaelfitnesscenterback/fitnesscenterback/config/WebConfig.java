package com.macaelfitnesscenterback.fitnesscenterback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Configuración personalizada de Spring MVC para el manejo de CORS (Cross-Origin Resource Sharing).
 * 
 * Esta clase permite que el frontend alojado en un origen distinto
 * pueda realizar peticiones HTTP al backend bajo las rutas "/api/**".
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura los mapeos CORS globales para los endpoints REST del backend.
     * 
     * - Permite solicitudes desde el origen http://localhost:5173 (común para el frontend en desarrollo).
     * - Habilita los métodos HTTP comunes (GET, POST, PUT, DELETE).
     * - Permite el uso de credenciales como cookies o encabezados de autorización.
     *
     * @param registry Objeto que gestiona los registros de configuración CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica esta configuración a todas las rutas que empiecen con /api/
            .allowedOriginPatterns("http://localhost:5173") // Origen permitido 
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
            .allowCredentials(true); // Permitir envío de cookies o headers de autorización
    }
}
