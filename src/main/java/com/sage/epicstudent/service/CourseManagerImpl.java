package com.sage.epicstudent.service;

import com.sage.epicstudent.domain.Course;
import com.sage.epicstudent.dto.CourseDTO;
import com.sage.epicstudent.dto.CoursesDTO;
import com.sage.epicstudent.exception.ApiException;
import com.sage.epicstudent.repository.CourseRepository;
import com.sage.epicstudent.service.manager.CourseManager;
import com.sage.epicstudent.validation.CourseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseManagerImpl implements CourseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseManagerImpl.class);

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional
    public void create(CourseDTO courseDTO) {
        LOGGER.info("Creating course {}", courseDTO.getName());
        courseValidator.validateStudent(courseDTO);
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void update(Long courseId, CourseDTO courseDTO) {
        LOGGER.info("Updating course for id {}", courseId);
        Optional<Course> courseOptional = getCourseById(courseId);
        courseOptional.orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));
        Course course = courseOptional.get();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting course by id {}", id);
        Optional<Course> courseOptional = getCourseById(id);
        courseOptional.orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));
        courseRepository.delete(courseOptional.get());
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Map<Long, Course> fetchCoursesByIds(Set<Long> ids) {
        return courseRepository.findAllById(ids).stream().collect(Collectors.toMap(course -> course.getId(), course -> course));
    }

    @Override
    public CoursesDTO fetchCourses(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Course> courses = courseRepository.findAll(paging);
        return buildCoursesResponse(courses);
    }

    private CoursesDTO buildCoursesResponse(Page<Course> courses) {
        CoursesDTO coursesDTO = new CoursesDTO();
        coursesDTO.setCourses(convertToStudentDTO(courses.getContent()));
        coursesDTO.setPageNumber(courses.getPageable().getPageNumber());
        coursesDTO.setPageSize(courses.getSize());
        coursesDTO.setTotalPage(courses.getTotalPages());
        coursesDTO.setTotalElements(courses.getTotalElements());
        return coursesDTO;
    }

    private List<CourseDTO> convertToStudentDTO(List<Course> content) {
        return content.parallelStream().map(course ->
                CourseDTO.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .build())
                .collect(Collectors.toList());

    }

}
