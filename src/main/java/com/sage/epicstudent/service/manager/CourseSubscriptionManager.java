package com.sage.epicstudent.service.manager;

import com.sage.epicstudent.domain.Course;
import com.sage.epicstudent.domain.Student;
import com.sage.epicstudent.dto.CreateSubscriptionDTO;
import com.sage.epicstudent.dto.SubscriptionReport;
import com.sage.epicstudent.dto.SubscriptionResponseDTO;

public interface CourseSubscriptionManager {


    SubscriptionResponseDTO handleSubscription(CreateSubscriptionDTO createSubscriptionDTO);
    void subscribe(Course course, Student student);
    SubscriptionReport getSubscriptionReport(int page, int size);
}
