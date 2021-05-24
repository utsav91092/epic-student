package com.sage.epicstudent.controller;

import com.sage.epicstudent.dto.CourseDTO;
import com.sage.epicstudent.exception.ApiException;
import com.sage.epicstudent.service.manager.CourseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseManager courseManager;

    //Api level auth authorization can be added here based on roles and privilege
    @PostMapping(value = "/course/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCourse(@RequestBody CourseDTO courseDTO) {
        try {
            LOGGER.debug("In create course");
            courseManager.create(courseDTO);
            LOGGER.info("course created successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating course", e);
            throw new ApiException("Failed to create course", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //We can create a separate PATCH endpoint for partial updates
    @PutMapping(value = "/course/{courseId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        try {
            LOGGER.debug("In update course");
            courseManager.update(courseId, courseDTO);
            LOGGER.info("course updated successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while updating course", e);
            throw new ApiException("Failed to update course", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/course/delete/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable("courseId") Long courseId) {
        try {
            LOGGER.debug("In delete course");
            courseManager.delete(courseId);
            LOGGER.info("course deleted successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while deleting course", e);
            throw new ApiException("Failed to delete course", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/course")
    public ResponseEntity getCourses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        try {
            return new ResponseEntity(courseManager.fetchCourses(page, size), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error while deleting course", e);
            throw new ApiException("Failed to delete course", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
