package com.sage.epicstudent.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubscriptionReport extends BasePageEntity {

    private List<SubscriptionReport.Subscription> subscriptions;

    @Getter
    @Setter
    public static class Subscription{
        private String studentName;
        private String courseName;

        public void setStudentName(String firstName, String lastName) {
            this.studentName = firstName + " "+ lastName;
        }
    }


}
