package com.sage.epicstudent.validation;

import com.sage.epicstudent.dto.StudentDTO;
import com.sage.epicstudent.exception.ApiException;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentValidator.class);

    public void validateStudent(StudentDTO student) {
        //Custom validation logic can be placed here based on business requirement. Ex: Length, Pattern etc
        LOGGER.debug("Validating student");
        if (StringUtil.isEmpty(student.getFirstName())) {
            throw new ApiException("First name is mandatory", HttpStatus.BAD_REQUEST);
        }
        if (StringUtil.isEmpty(student.getLastName())) {
            throw new ApiException("Last name is mandatory", HttpStatus.BAD_REQUEST);
        }
        if (StringUtil.isEmpty(student.getPhoneNumber())) {
            throw new ApiException("Phone number is mandatory", HttpStatus.BAD_REQUEST);
        }
    }
}
