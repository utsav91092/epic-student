package com.sage.epicstudent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDTO {

    private Long id;
    private String name;
    private String description;

    @Tolerate
    public CourseDTO() {}

}
