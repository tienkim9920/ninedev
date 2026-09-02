package com.basicspringboot.ninedev.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.basicspringboot.ninedev.exceptions.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final HandlerExceptionResolver exceptionResolver;

    public JwtFilter(JwtUtils jwtUtils, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtUtils = jwtUtils;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        try {
            if (StringUtils.hasText(authHeader) && StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
                String token = authHeader.substring(7);

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
                } else
                    throw new UnauthorizedException("Token Invalid");
            } else
                throw new UnauthorizedException("Token required");

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            exceptionResolver.resolveException(request, response, null, e);
        }
    }
}
