package com.macaelfitnesscenterback.fitnesscenterback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica esta configuración a todas las rutas que empiecen con /api/
            .allowedOriginPatterns("http://localhost:5173") // Origen permitido 
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
            .allowCredentials(true); // Permitir envío de cookies o headers de autorización
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/images/noticias/**")
            .addResourceLocations("file:uploads/images/noticias/","file:src/main/uploads/images/noticias/");
    }
}
