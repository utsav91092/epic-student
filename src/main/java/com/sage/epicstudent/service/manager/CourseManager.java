package com.sage.epicstudent.service.manager;

import com.sage.epicstudent.domain.Course;
import com.sage.epicstudent.dto.CourseDTO;
import com.sage.epicstudent.dto.CoursesDTO;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CourseManager {

    void create(CourseDTO courseDTO);
    void update(Long courseId, CourseDTO studentDTO);
    void delete(Long id);
    CoursesDTO fetchCourses(int page, int size);
    Optional<Course> getCourseById(Long courseId);
    Map<Long, Course> fetchCoursesByIds(Set<Long> ids);

}
