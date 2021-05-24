package com.sage.epicstudent.controller;

import com.sage.epicstudent.dto.StudentDTO;
import com.sage.epicstudent.exception.ApiException;
import com.sage.epicstudent.service.manager.StudentManager;
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
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentManager studentManager;

    //Api level auth authorization can be added here based on roles and privilege
    @PostMapping(value = "/student/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createStudent(@RequestBody StudentDTO studentDTO) {
        try {
            LOGGER.debug("In create student");
            studentManager.create(studentDTO);
            LOGGER.info("Student created successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating student", e);
            throw new ApiException("Failed to create student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //We can create a separate PATCH endpoint for partial updates
    @PutMapping(value = "/student/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateStudent(@RequestBody StudentDTO studentDTO) {
        try {
            LOGGER.debug("In update student");
            studentManager.update(studentDTO);
            LOGGER.info("Student updated successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while updating student", e);
            throw new ApiException("Failed to update student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/student/delete/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") Long studentId) {
        try {
            LOGGER.debug("In delete student");
            studentManager.delete(studentId);
            LOGGER.info("Student deleted successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while deleting student", e);
            throw new ApiException("Failed to delete student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/student")
    public ResponseEntity getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        try {
            return new ResponseEntity(studentManager.fetchStudents(page, size), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error while deleting student", e);
            throw new ApiException("Failed to delete student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
