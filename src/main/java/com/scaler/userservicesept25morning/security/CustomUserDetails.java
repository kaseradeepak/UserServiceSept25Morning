package com.scaler.userservicesept25morning.security;

import com.scaler.userservicesept25morning.models.Role;
import com.scaler.userservicesept25morning.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CustomGranterAuthority> customGranterAuthorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            customGranterAuthorities.add(new CustomGranterAuthority(role));
        }

        return customGranterAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
