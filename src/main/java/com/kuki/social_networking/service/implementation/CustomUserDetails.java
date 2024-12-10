package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public static CustomUserDetails fromUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());

        authorities.addAll(user.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
            .collect(Collectors.toSet()));

        return new CustomUserDetails(username, password, authorities);
    }
}
