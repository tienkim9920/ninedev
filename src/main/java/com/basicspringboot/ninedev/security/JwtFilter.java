package com.basicspringboot.ninedev.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.basicspringboot.ninedev.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (jwtUtils.isTokenValid(token)) {
                    String username = jwtUtils.extractUsername(token);
                    String role = jwtUtils.extractRole(token);

                    List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                    if (StringUtils.hasText(role)) {
                        String formatedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                        authorities = List.of(new SimpleGrantedAuthority(formatedRole));
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                            null,
                            authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json");

                    ResponseDTO dto = new ResponseDTO(
                            401, false, "Token invalid", null);
                    res.getWriter().write(new ObjectMapper().writeValueAsString(dto));
                    return;
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");

            ResponseDTO dto = new ResponseDTO(
                    401, false, "Token required", null);
            res.getWriter().write(new ObjectMapper().writeValueAsString(dto));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
