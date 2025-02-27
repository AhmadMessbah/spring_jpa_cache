package com.spring_jpa_cache.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

//         Add roles
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

//         Add permissions
        user.getRoles().forEach(role -> role.getPermissions()
                .forEach(
                        permission ->
                                authorities.add(new SimpleGrantedAuthority(permission.getName())))
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (user.getCredentialsExpiryDate() != null) {
            return user.getCredentialsExpiryDate().isAfter(LocalDateTime.now());
        }
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
