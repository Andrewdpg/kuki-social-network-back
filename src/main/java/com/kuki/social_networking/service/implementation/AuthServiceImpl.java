package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.service.interfaces.AuthService;
import com.kuki.social_networking.service.interfaces.UserService;
import com.kuki.social_networking.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * Service class for handling authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    /**
     * Authenticates a user based on email and password.
     *
     * @param username The user's nickname.
     * @param password The user's password.
     * @return JWT token.
     */
    @Override
    public String authenticateUser(String username, String password) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        Map<String, Object> claims = Map.of("photoUrl", userService.getUserByUsername(username).getPhotoUrl());

        return jwtTokenUtils.generateToken((CustomUserDetails) auth.getPrincipal(), claims);
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated user.
     */
    @Override
    public CustomUserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof String) {
            return null;
        }
        return (CustomUserDetails) authentication.getPrincipal();
    }

    /**
     * Logs out the currently authenticated user.
     */
    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();
    }
}
