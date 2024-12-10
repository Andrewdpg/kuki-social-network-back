package com.kuki.social_networking.config;

public class Path {

    public static final String BASE_URL = "http://localhost:8080";

    public static final String[] PUBLIC_PATHS = new String[]{"/api/v1/auth/**", "/api/v1/recipes/**", "/api/v1/users/**", "/api/v1/steps/**", "/api/v1/comment/**", "/api/v1/ingredients/**", "/api/v1/planner/**", "/api/v1/notifications/**", "/api/v1/tag/**", "/api/v1/follow/**", "/api/v1/country/**", "/login"};
    public static final String[] USER_PATHS = new String[]{"/user/**"};
    public static final String[] ADMIN_PATHS = new String[]{"/admin/**"};

    public static final String VERIFICATION_PATH = BASE_URL + "/api/v1/registration/confirm";
}
