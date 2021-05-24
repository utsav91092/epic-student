package com.sage.epicstudent.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoursesDTO extends BasePageEntity{

    List<CourseDTO> courses;

}
