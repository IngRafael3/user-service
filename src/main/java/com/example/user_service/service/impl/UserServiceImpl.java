package com.example.user_service.service.impl;

import com.example.user_service.models.UserEntity;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id)
               .doOnError(error -> logger.error("Error retrieving user: {}", error.getMessage()));
    }

    @Override
    public Flux<UserEntity> getAllUsers() {
        return userRepository.findAll()
                .doOnSubscribe(subscription -> logger.info("Request to retrieve all users"))
                .doOnError(error -> logger.error("Error retrieving users: {}", error.getMessage()))
                .doOnComplete(() -> logger.info("Completed retrieving all users"));
    }

    @Override
    public Mono<UserEntity> createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<UserEntity> updateUser(Long id, UserEntity user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return userRepository.findById(id)
                .flatMap(userRepository::delete);
    }
}
