package com.example.user_service.handlers;

public class NotFoundUser extends RuntimeException{

    public NotFoundUser(String message) {
        super(message);
    }
}
