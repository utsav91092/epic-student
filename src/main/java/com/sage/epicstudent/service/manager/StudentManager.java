package com.sage.epicstudent.service.manager;

import com.sage.epicstudent.domain.Student;
import com.sage.epicstudent.dto.StudentDTO;
import com.sage.epicstudent.dto.StudentsDTO;

import java.util.Map;
import java.util.Set;

public interface StudentManager {
    void create(StudentDTO studentDTO);
    void update(StudentDTO studentDTO);
    void delete(Long id);
    StudentsDTO fetchStudents(int page, int size);
    Map<Long,Student> fetchStudentsByIds(Set<Long> ids);
}
