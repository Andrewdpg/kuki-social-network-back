package com.kuki.social_networking.config.filter;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticatedUserRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        assert filterChain != null;
        assert response != null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (request.getRequestURI().equals("/login") && authentication != null && authentication.isAuthenticated()) {
            response.sendRedirect("/admin/user");
            return;
        }
        filterChain.doFilter(request, response);
    }
}