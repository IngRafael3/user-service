package com.example.user_service.handlers;


import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundUser.class})
    public Mono<ResponseEntity<String>> hadlerNotFoundException (NotFoundUser ex ){

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }


    @ExceptionHandler({InvalidUserExceptions.class})
    public Mono<ResponseEntity<String>> handleInvalidUserException(InvalidUserExceptions ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }
}
