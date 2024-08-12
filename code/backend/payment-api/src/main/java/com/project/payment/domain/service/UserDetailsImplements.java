package com.project.payment.domain.service;

import com.project.payment.api.auth.UserAuthenticated;
import com.project.payment.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsImplements implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
