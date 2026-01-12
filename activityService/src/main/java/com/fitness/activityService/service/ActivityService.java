package com.fitness.activityService.service;

import com.fitness.activityService.repositoy.ActivityRepository;
import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository repository;
    private final UserValidationService userValidationService;
    public ActivityResponse trackActivity(ActivityRequest request){
        boolean isValidUser = userValidationService
                .validateUser(request.getUserId());
        if(!isValidUser){
            throw new RuntimeException("invalid user "+ request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .additionalMatrics(request.getAdditionalMatrics())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .duration(request.getDuration())
                .build();
        Activity savedActivity = repository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){

        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMatrics(activity.getAdditionalMatrics());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response ;
    }

    public List<ActivityResponse> getUserActivities(String userId) {

        List<Activity> activities = repository.findByUserId(userId);
        return  activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivity(String activityId) {
        return repository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity Not found"));
    }
}
