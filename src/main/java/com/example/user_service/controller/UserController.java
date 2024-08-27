package com.example.user_service.controller;

import com.example.user_service.handlers.InvalidUserExceptions;
import com.example.user_service.handlers.NotFoundUser;
import com.example.user_service.models.UserEntity;
import com.example.user_service.service.UserService;
import com.example.user_service.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/{id}")
    public Mono<UserEntity> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .switchIfEmpty(Mono.error(new NotFoundUser("User no found with id: "+id)));
    }

    @GetMapping
    public Flux<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

/*    @PostMapping
    public Mono<UserEntity> createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserEntity> createUser(@RequestBody Mono<UserEntity> userMono) {
        return userMono.flatMap(user -> {
            Errors errors = new BeanPropertyBindingResult(user, "user");
            userValidator.validate(user, errors);

            if (errors.hasErrors()) {
                return Mono.error(new InvalidUserExceptions("Error generando users"));
            }

            return userService.createUser(user);
        });
    }

    @PutMapping("/{id}")
    public Mono<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user)
                .switchIfEmpty(Mono.error(new NotFoundUser("User no found with id: "+id)));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .switchIfEmpty(Mono.error(new NotFoundUser("User no found with id: "+id)));
    }
}

