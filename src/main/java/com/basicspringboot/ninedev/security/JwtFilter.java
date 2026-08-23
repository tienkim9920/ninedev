package com.basicspringboot.ninedev.security;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter {
    private static final List<String> PUBLIC_ROUTES = List.of("/login");

    protected boolean shouldNotFilter(HttpServletRequest request) {
        return PUBLIC_ROUTES.contains(request.getServletPath());
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}
