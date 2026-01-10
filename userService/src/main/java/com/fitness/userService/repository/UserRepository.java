package com.fitness.userService.repository;

import com.fitness.userService.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(@NotBlank(message = "Email is required") String email);
}
