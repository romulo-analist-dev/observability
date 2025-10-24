package com.romulo.observability;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_KEY = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Gera um ID único para a requisição
            String traceId = UUID.randomUUID().toString();
            // Adiciona o ID ao MDC
            MDC.put(TRACE_ID_KEY, traceId);
            filterChain.doFilter(request, response);
        } finally {
            // Limpa o MDC no final para evitar memory leaks e dados incorretos em outras threads
            MDC.remove(TRACE_ID_KEY);
        }
    }
}
