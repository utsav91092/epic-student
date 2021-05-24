package com.sage.epicstudent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phoneNumber;

    @Tolerate
    public StudentDTO() {}
}
