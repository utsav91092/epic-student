package com.sage.epicstudent.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentsDTO extends BasePageEntity{

    List<StudentDTO> students;

}
