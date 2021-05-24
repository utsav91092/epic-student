package com.sage.epicstudent.validation;

import com.sage.epicstudent.dto.CourseDTO;
import com.sage.epicstudent.exception.ApiException;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CourseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseValidator.class);

    public void validateStudent(CourseDTO courseDTO) {
        //Custom validation logic can be placed here based on business requirement
        LOGGER.debug("Validating course");
        if (StringUtil.isEmpty(courseDTO.getName())) {
            throw new ApiException("Course name is mandatory", HttpStatus.BAD_REQUEST);
        }

    }
}
