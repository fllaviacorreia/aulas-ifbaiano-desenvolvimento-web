package com.project.payment.api.auth;

import com.project.payment.domain.model.RolesUser;
import com.project.payment.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UserAuthenticated implements UserDetails {
    private final User userModel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(userModel.getRole().equals(RolesUser.CLIENT))
            return Collections.singleton(new SimpleGrantedAuthority("USER"));
        else
            return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userModel.isActive();
    }

}
