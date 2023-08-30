package com.edmwat.cards.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserDetailsConfig {
    @Bean
    public InMemoryUserDetailsManager user(){
        return new InMemoryUserDetailsManager(
                User.withUsername("admin@gmail.com")
                        .password("{noop}password")
                        .authorities("ROLE_ADMIN")
                        .build(),
                User.withUsername("user1@gmail.com")
                        .password("{noop}password")
                        .authorities("ROLE_MEMBER")
                        .build(),
                User.withUsername("user2@gmail.com")
                        .password("{noop}password")
                        .authorities("ROLE_MEMBER")
                        .build()

        );
    }
}
