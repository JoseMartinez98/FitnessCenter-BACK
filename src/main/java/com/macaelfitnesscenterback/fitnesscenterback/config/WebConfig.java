package com.macaelfitnesscenterback.fitnesscenterback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/images/noticias/**")
        .addResourceLocations("file:uploads/images/noticias/", "file:src/main/uploads/images/noticias/", "file:src/uploads/images/noticias/");
    registry
        .addResourceHandler("/documents/**")
        .addResourceLocations("file:uploads/documents/", "file:src/main/uploads/documents/", "file:src/uploads/documents/");
    registry
        .addResourceHandler("/images/planes/**")
        .addResourceLocations("file:uploads/images/planes/", "file:src/main/uploads/images/planes/", "file:src/uploads/images/planes/");
    registry
        .addResourceHandler("/images/Horario/**")
        .addResourceLocations("file:uploads/images/Horario/", "file:src/main/uploads/images/Horario/", "file:src/uploads/images/Horario/");

}

}
