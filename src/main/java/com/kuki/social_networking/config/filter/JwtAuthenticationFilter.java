package com.kuki.social_networking.config.filter;

import com.kuki.social_networking.util.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.kuki.social_networking.util.JwtTokenUtils.getTokenFromRequest;

/**
 * Filter for handling JWT authentication.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtTokenUtils tokenUtils;

    /**
     * Filters each HTTP request to check for a valid JWT token and sets the authentication context if valid.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(tokenUtils.validateToken(token)){
            UserDetails userDetails = tokenUtils.getUserDetailsFromToken(token);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
