package com.basicspringboot.ninedev.security;

import com.basicspringboot.ninedev.dto.ResponseDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

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

            HttpServletResponse res = (HttpServletResponse) response;

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");

            ResponseDTO dto = new ResponseDTO(
                    401,
                    false,
                    "Token required",
                    null
            );

            res.getWriter().write(new ObjectMapper().writeValueAsString(dto));
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {

            HttpServletResponse res = (HttpServletResponse) response;

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");

            ResponseDTO dto = new ResponseDTO(
                    401,
                    false,
                    "Token invalid",
                    null
            );

            res.getWriter().write(new ObjectMapper().writeValueAsString(dto));
            return;
        }

        chain.doFilter(request, response);
    }
}