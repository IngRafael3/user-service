package com.example.user_service.utils;

import com.example.user_service.models.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;

        if (user.getName() == null || user.getName().isEmpty()) {
            errors.rejectValue("name", "user.name.empty", "User name cannot be empty");
        }

    }

}
