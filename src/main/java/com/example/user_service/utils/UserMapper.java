package com.example.user_service.utils;

import com.example.user_service.dto.UserDTO;
import com.example.user_service.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

public  UserDTO toUserDTO(UserEntity userEntity){
    UserDTO dto = new UserDTO();
    dto.setId(userEntity.getId());
    dto.setName(userEntity.getName());
    dto.setEmail(userEntity.getEmail());
    dto.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    return dto;

}
}
