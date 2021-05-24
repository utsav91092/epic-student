package com.sage.epicstudent.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CreateSubscriptionDTO {
    //studentId, courseId
    Map<Long, Long> subscriptions;


}
