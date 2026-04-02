package com.nudgebank.paymentbackend.common.config;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    private final List<String> allowedOriginPatterns;

    public WebCorsConfig(Environment environment) {
        this.allowedOriginPatterns = Binder.get(environment)
                .bind("app.cors.allowed-origin-patterns", Bindable.listOf(String.class))
                .orElse(List.of(
                        "http://localhost:3000",
                        "http://localhost:5174",
                        "https://*.ngrok-free.dev"
                ));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(allowedOriginPatterns.toArray(String[]::new))
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
