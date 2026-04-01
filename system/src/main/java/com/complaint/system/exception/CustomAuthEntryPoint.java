package com.complaint.system.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        
        String path = request.getRequestURI();
        
        // Don't block Swagger paths
        if (path.startsWith("/v3/api-docs") || 
            path.startsWith("/swagger-ui") ||
            path.startsWith("/webjars")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),
            Map.of("status", 401, "message", "Invalid or missing token. Please login again."));
    }
}
