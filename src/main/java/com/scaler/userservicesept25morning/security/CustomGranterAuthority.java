package com.scaler.userservicesept25morning.security;

import com.scaler.userservicesept25morning.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGranterAuthority implements GrantedAuthority {
    private Role role;

    public CustomGranterAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getValue();
    }
}
