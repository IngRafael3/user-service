package com.example.user_service.dto;

import com.example.user_service.models.UserEntity;

import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<TaskDTO> tasks;

    public UserDTO(Long id, String name, String email, String password, List<TaskDTO> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tasks = tasks;
    }

    public UserDTO() {
    }

    public UserDTO(UserEntity userEntity) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
