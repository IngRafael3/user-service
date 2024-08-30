package com.example.user_service.UserServiceImplTest;

import com.example.user_service.dto.TaskDTO;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.models.UserEntity;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*    @Test
    void testGetUserById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setEmail("john@example.com");
        userEntity.setPassword("password123");

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Sample Task");

        when(userRepository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(userService.getTaksFormUser("john@example.com")).thenReturn(Flux.just(taskDTO));

        Mono<UserDTO> userDTO = userService.getUserById(1L);

        StepVerifier.create(userDTO)
                .expectNextMatches(user -> user.getId().equals(1L) &&
                        user.getName().equals("John Doe") &&
                        user.getEmail().equals("john@example.com") &&
                        user.getTasks().contains(taskDTO))
                .verifyComplete();
    }*/

    @Test
    void testGetAllUsers() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setName("John Doe");

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setName("Jane Doe");

        when(userRepository.findAll()).thenReturn(Flux.just(userEntity1, userEntity2));

        Flux<UserEntity> userFlux = userService.getAllUsers();

        StepVerifier.create(userFlux)
                .expectNextMatches(user -> user.getId().equals(1L) && user.getName().equals("John Doe"))
                .expectNextMatches(user -> user.getId().equals(2L) && user.getName().equals("Jane Doe"))
                .verifyComplete();
    }

    @Test
    void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");

        when(userRepository.save(userEntity)).thenReturn(Mono.just(userEntity));

        Mono<UserEntity> createdUser = userService.createUser(userEntity);

        StepVerifier.create(createdUser)
                .expectNextMatches(user -> user.getId().equals(1L) && user.getName().equals("John Doe"))
                .verifyComplete();
    }

    @Test
    void testUpdateUser() {
        UserEntity existingUser = new UserEntity();
        existingUser.setId(1L);
        existingUser.setName("John Doe");

        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(1L);
        updatedUser.setName("Jane Doe");

        when(userRepository.findById(1L)).thenReturn(Mono.just(existingUser));
        when(userRepository.save(existingUser)).thenReturn(Mono.just(updatedUser));

        Mono<UserEntity> result = userService.updateUser(1L, updatedUser);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getName().equals("Jane Doe"))
                .verifyComplete();
    }

    @Test
    void testDeleteUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(userRepository.delete(userEntity)).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteUser(1L);

        StepVerifier.create(result)
                .verifyComplete();
    }
}





