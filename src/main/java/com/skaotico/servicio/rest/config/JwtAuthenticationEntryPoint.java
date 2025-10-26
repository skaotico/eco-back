package com.skaotico.servicio.rest.config;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ApiResponseGeneric<Object> apiResponse = new ApiResponseGeneric<>(
                false,
                null,
                "Autenticación fallida: token JWT inválido o no proporcionado | Path: "
                        + request.getRequestURI()
                        + " | Timestamp: " + Instant.now(),null,null,null
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
