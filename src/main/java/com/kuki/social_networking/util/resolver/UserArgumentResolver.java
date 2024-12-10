package com.kuki.social_networking.util.resolver;

import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.AuthService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Resolves the authenticated user from the SecurityContextHolder.
 */
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    /**
     * Checks if the parameter is annotated with AuthenticatedUser and is of type User.
     *
     * @param parameter the method parameter
     * @return true if the parameter is annotated with AuthenticatedUser and is of type User
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticatedUser.class) != null &&
            CustomUserDetails.class.isAssignableFrom(parameter.getParameterType());
    }

    /**
     * Resolves the authenticated user from the SecurityContextHolder.
     *
     * @param parameter     the method parameter
     * @param mavContainer  the model and view container
     * @param webRequest    the web request
     * @param binderFactory the web data binder factory
     * @return the authenticated user
     */
    @Override
    public CustomUserDetails resolveArgument(@Nullable MethodParameter parameter,
                                             ModelAndViewContainer mavContainer,
                                             @Nullable NativeWebRequest webRequest,
                                             org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        return authService.getAuthenticatedUser();
    }
}

