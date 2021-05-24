package com.sage.epicstudent.repository;

import com.sage.epicstudent.domain.CourseSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseSubscriptionRepository extends JpaRepository<CourseSubscription, Long> {

    @Query("from CourseSubscription where student.id = :studentId and course.id = :courseId")
    Optional<CourseSubscription> findByStudentIdAndCourseId(Long studentId, Long courseId);

}
