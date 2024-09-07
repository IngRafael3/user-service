package com.example.user_service.controller;

import com.example.user_service.config.JwtUtils;
import com.example.user_service.dto.AuthUser;
import com.example.user_service.models.UserEntity;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Mono<?> createUser(@RequestBody UserEntity user){
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthUser userDTO){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.email(),userDTO.password()))
                .map(auth->{
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    String jwt = jwtUtils.generateToken(userDetails.getUsername());
                    return ResponseEntity.ok(jwt);
                })
                .onErrorResume(e->Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
