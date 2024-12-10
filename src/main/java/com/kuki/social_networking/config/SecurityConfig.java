package com.kuki.social_networking.config;

import com.kuki.social_networking.config.filter.AuthenticatedUserRedirectFilter;
import com.kuki.social_networking.config.filter.CustomAuthenticationSuccessHandler;
import com.kuki.social_networking.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.kuki.social_networking.config.Path.ADMIN_PATHS;
import static com.kuki.social_networking.config.Path.PUBLIC_PATHS;
import static com.kuki.social_networking.config.Path.USER_PATHS;

/**
 * Application configuration class for setting up security.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final AuthenticatedUserRedirectFilter redirectAuthenticatedUserFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Configures the security filter chain for HTTP security.
     *
     * @param http the HttpSecurity object to be configured
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .authorizeHttpRequests(this::authorizationManager)
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Configures the security filter chain for HTTP security.
     *
     * @param http the HttpSecurity object to be configured
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/login","/admin/**")
            .authorizeHttpRequests(this::authorizationManager)
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .authenticationProvider(authProvider)
            .addFilterBefore(redirectAuthenticatedUserFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Configures the authorization manager for the HTTP security.
     *
     * @param auth the AuthorizationManagerRequestMatcherRegistry object to be configured
     */
    private void authorizationManager(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
            .requestMatchers(ADMIN_PATHS).hasAuthority("ADMIN_PANEL")
            .requestMatchers(USER_PATHS).hasRole("DEFAULT")
            .requestMatchers(PUBLIC_PATHS).permitAll()
            .anyRequest().authenticated();
    }

}
