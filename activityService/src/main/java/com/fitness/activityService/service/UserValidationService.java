package com.fitness.activityService.service;

import com.fitness.activityService.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        try {
            return Boolean.TRUE.equals(
                    userServiceWebClient
                            .get()
                            .uri("/api/{userId}/validate", userId)
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .block()
            );
        } catch (WebClientResponseException e) {

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User not found: " + userId);
            }

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid request for user: " + userId);
            }

            throw new RuntimeException(
                    "User service error: " + e.getStatusCode(), e
            );
        }
    }
}
