package com.basicspringboot.ninedev.security;

import com.basicspringboot.ninedev.exceptions.UnauthorizedException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    private static final List<String> PUBLIC_ROUTES = List.of(
            "/auth/login"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        // nếu route public thì cho qua
        if (PUBLIC_ROUTES.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token required");
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            throw new UnauthorizedException("Token invalid");
        }

        chain.doFilter(request, response);
    }
}