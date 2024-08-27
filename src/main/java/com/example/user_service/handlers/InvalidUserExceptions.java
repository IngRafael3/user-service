package com.example.user_service.handlers;

public class InvalidUserExceptions extends RuntimeException{

    public InvalidUserExceptions(String message) {
        super(message);
    }
}
