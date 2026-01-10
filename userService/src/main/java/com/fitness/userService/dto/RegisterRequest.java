package com.fitness.userService.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "password must not be blank!!")
    @Size(min = 6,message = "password must be of 6 characters")
    private String password;

}
