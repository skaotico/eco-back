package com.skaotico.servicio.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtDecoder jwtDecoder;

    public SecurityConfig(@Qualifier("jwtDecoderBean") JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint = new AuthenticationEntryPoint() {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException {

            ApiResponseGeneric<Object> apiResponse = new ApiResponseGeneric<>(
                    false,
                    null,
                    "Autenticacion fallida: token JWT invalido o no proporcionado | Path: "
                            + request.getRequestURI()
                            + " | Timestamp: " + Instant.now(),
                    null,
                    null,
                    null
            );

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(List.of("http://localhost:*", "http://192.168.1.3:*"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso público a Swagger
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                        .requestMatchers("/usuario/**", "/arbol/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder))
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                );

        return http.build();
    }
}
