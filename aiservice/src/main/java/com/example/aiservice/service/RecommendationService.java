package com.example.aiservice.service;

import com.example.aiservice.model.Recommendation;
import com.example.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.RuntimeOperationsException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository repository;

    public List<Recommendation> getUserRecommendation(String userId) {
        List<Recommendation> recommendations = repository.findByUserId(userId);
        if(recommendations.isEmpty()){
            throw new RuntimeException("UserNot Found :: ma chuda !!  " + userId );
        }
        return recommendations;
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return repository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found : " + activityId));
    }


}
