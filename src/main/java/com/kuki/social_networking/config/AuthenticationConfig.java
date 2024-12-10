package com.kuki.social_networking.config;

import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Application configuration class for setting up authentication and user details services.
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserRepository userRepository;

    /**
     * Creates and configures the authentication manager bean.
     *
     * @param config the AuthenticationConfiguration object
     * @return the configured AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config ) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates and configures the authentication provider bean.
     *
     * @return the configured AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates and configures the user details service bean.
     *
     * @return the configured UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findById(username)
            .map(CustomUserDetails::fromUser)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /**
     * Creates and configures the password encoder bean.
     *
     * @return the configured PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
