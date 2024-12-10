package com.kuki.social_networking.config;

import com.kuki.social_networking.service.interfaces.AuthService;
import com.kuki.social_networking.util.resolver.UserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration class for web-related beans.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthService authService;

    /**
     * Add custom argument resolvers to the list of argument resolvers.
     *
     * @param resolvers The list of argument resolvers.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver(authService));
    }

    /**
     * Create a HiddenHttpMethodFilter bean.
     *
     * @return The HiddenHttpMethodFilter bean.
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}

