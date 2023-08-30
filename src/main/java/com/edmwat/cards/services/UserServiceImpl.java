package com.edmwat.cards.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final InMemoryUserDetailsManager userDetailsManager;
    private final TokenService tokenService;

    public UserServiceImpl(InMemoryUserDetailsManager userDetailsManager,
                           TokenService tokenService) {
        this.userDetailsManager = userDetailsManager;
        this.tokenService = tokenService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDetailsManager.loadUserByUsername(username);
    }

    public String generateToken(Authentication authentication){

        String token = tokenService.createToken(authentication);

        return token;
    }
}
