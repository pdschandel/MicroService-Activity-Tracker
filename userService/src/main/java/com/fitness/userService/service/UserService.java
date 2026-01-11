package com.fitness.userService.service;

import com.fitness.userService.dto.RegisterRequest;
import com.fitness.userService.dto.UserResponse;
import com.fitness.userService.model.User;
import com.fitness.userService.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public  UserResponse getUserProfile(String userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found."));
        UserResponse response = new UserResponse();
        response.setFirstName(user.getFirstName());
        response.setEmail(user.getEmail());
        response.setLastName(user.getLastName());
        response.setId(user.getId());
        response.setPassword(user.getPassword());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public Object register(@Valid RegisterRequest request) {
        if(repository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already Exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        user.setLastName(request.getLastName());

        User savedUser = repository.save(user);

        UserResponse response = new UserResponse();
        response.setFirstName(savedUser.getFirstName());
        response.setEmail(savedUser.getEmail());
        response.setLastName(savedUser.getLastName());
        response.setId(savedUser.getId());
        response.setPassword(savedUser.getPassword());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }

    public Boolean existsById(String userId) {
        return repository.existsById(userId);
    }
}
