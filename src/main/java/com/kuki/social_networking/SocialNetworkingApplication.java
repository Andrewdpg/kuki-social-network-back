package com.kuki.social_networking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Method documentation example.
 */
@SpringBootApplication
public class SocialNetworkingApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SocialNetworkingApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkingApplication.class, args);
    }

}
