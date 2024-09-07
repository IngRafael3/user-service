package com.example.user_service.config;

import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Array;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username){
        return userService.getUserByEmail(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("No found user ")))
                .map(userDTO-> new User(userDTO.getEmail(),userDTO.getPassword(), new ArrayList<>()));
    }
}
