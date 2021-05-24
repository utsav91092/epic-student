package com.sage.epicstudent.service;

import com.sage.epicstudent.domain.Student;
import com.sage.epicstudent.dto.StudentDTO;
import com.sage.epicstudent.dto.StudentsDTO;
import com.sage.epicstudent.exception.ApiException;
import com.sage.epicstudent.repository.StudentRepository;
import com.sage.epicstudent.service.manager.StudentManager;
import com.sage.epicstudent.validation.StudentValidator;
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
public class StudentManagerImpl implements StudentManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentManagerImpl.class);

    @Autowired
    private StudentValidator studentValidator;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public void create(StudentDTO studentDTO) {
        LOGGER.info("Creating student by name {}",studentDTO.getFirstName());
        studentValidator.validateStudent(studentDTO);
        //We can check on phone number or user name/ student id before creating student, to avoid duplicate student creation
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        Student createdStudent = studentRepository.save(student);
        LOGGER.info("Student created successfully, by id {}",createdStudent.getId());
    }

    @Override
    @Transactional
    public void update(StudentDTO studentDTO) {
        LOGGER.info("Updating student, id {}",studentDTO.getId());
        studentValidator.validateStudent(studentDTO);
        Optional<Student> studentOptional = studentRepository.findById(Long.parseLong(studentDTO.getId()));
        studentOptional.orElseThrow(() -> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        Student student = studentOptional.get();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting student, id {}",id);
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.orElseThrow(() -> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        //Can be soft delete also, based on the need basis we can update it.
        studentRepository.delete(studentOptional.get());
    }

    @Override
    public StudentsDTO fetchStudents(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAll(paging);
        return buildStudentsResponse(students);
    }

    @Override
    public Map<Long, Student> fetchStudentsByIds(Set<Long> ids) {
        return studentRepository.findAllById(ids).stream().collect(Collectors.toMap(student -> student.getId(), student -> student));
    }

    private StudentsDTO buildStudentsResponse(Page<Student> students) {
        StudentsDTO studentsDTO = new StudentsDTO();
        studentsDTO.setStudents(convertToStudentDTO(students.getContent()));
        studentsDTO.setPageNumber(students.getPageable().getPageNumber());
        studentsDTO.setPageSize(students.getSize());
        studentsDTO.setTotalPage(students.getTotalPages());
        studentsDTO.setTotalElements(students.getTotalElements());
        return studentsDTO;
    }

    private List<StudentDTO> convertToStudentDTO(List<Student> content) {
        return content.parallelStream().map(student ->
                StudentDTO.builder()
                        .id(student.getId().toString())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .dateOfBirth(student.getDateOfBirth())
                        .phoneNumber(student.getPhoneNumber()).build()).collect(Collectors.toList());

    }

}
