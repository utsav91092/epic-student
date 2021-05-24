package com.sage.epicstudent.controller;

import com.sage.epicstudent.dto.CreateSubscriptionDTO;
import com.sage.epicstudent.exception.ApiException;
import com.sage.epicstudent.service.manager.CourseSubscriptionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSubscriptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseSubscriptionController.class);

    @Autowired
    private CourseSubscriptionManager courseSubscriptionManager;

    @PostMapping(value = "/course/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity subscribe(@RequestBody CreateSubscriptionDTO createSubscriptionDTO) {
        try {
            LOGGER.debug("In subscribe");
            courseSubscriptionManager.handleSubscription(createSubscriptionDTO);
            LOGGER.info("Subscription created successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating subscription", e);
            throw new ApiException("Failed to create subscription", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/courseSubscription/report")
    public ResponseEntity generateSubscriptionReport(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            LOGGER.debug("In generateSubscriptionReport");
            return new ResponseEntity(courseSubscriptionManager.getSubscriptionReport(page, size), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error while generating subscription report", e);
            throw new ApiException("Error while generating subscription report", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
