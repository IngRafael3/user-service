package com.example.user_service.service;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.models.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    //Mono<UserEntity> getUserById(Long id);

    Mono<UserDTO> getUserById(Long id);

    Flux<UserEntity> getAllUsers();

    Mono<UserEntity> createUser(UserEntity user);

    Mono<UserEntity> updateUser(Long id, UserEntity user);

    Mono<Void> deleteUser(Long id);


}
