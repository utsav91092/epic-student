package com.sage.epicstudent.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SubscriptionResponseDTO {

    private Map<String, String> subscriptionSuccessList = new HashMap<>();
    private Map<String, String> subscriptionExistsList = new HashMap<>();
    private Map<String, String> subscriptionFailedList = new HashMap<>();

    public void addSuccessfulSubscription(String student, String course){
        subscriptionSuccessList.put(student, course);
    }

    public void addExistsSubscription(String student, String course){
        subscriptionExistsList.put(student, course);
    }

    public void addFailedSubscription(String student, String course){
        subscriptionFailedList.put(student, course);
    }
}
