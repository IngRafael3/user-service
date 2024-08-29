package com.example.user_service.service.impl;

import com.example.user_service.dto.TaskDTO;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.models.UserEntity;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/api/tasks").build();
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

/*    @Override
    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id)
               .doOnError(error -> logger.error("Error retrieving user: {}", error.getMessage()));
    }*/

    @Override
    public Mono<UserDTO> getUserById(Long id){
        return  userRepository.findById(id).flatMap( user ->{
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            return getTaksFormUser(user.getEmail() )
                    .collectList()
                    .flatMap(taskDTOS ->{
                        userDTO.setTasks(taskDTOS);
                        return Mono.just(userDTO);
                    });

        });
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


    public Flux<TaskDTO> getTaksFormUser(String email){
        return webClient.get()
                .uri("/user/"+email)
                .retrieve()
                .bodyToFlux(TaskDTO.class);
    }
}
