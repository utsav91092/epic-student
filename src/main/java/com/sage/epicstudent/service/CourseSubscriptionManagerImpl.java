package com.sage.epicstudent.service;

import com.sage.epicstudent.domain.Course;
import com.sage.epicstudent.domain.CourseSubscription;
import com.sage.epicstudent.domain.Student;
import com.sage.epicstudent.domain.SubscriptionType;
import com.sage.epicstudent.dto.CreateSubscriptionDTO;
import com.sage.epicstudent.dto.SubscriptionReport;
import com.sage.epicstudent.dto.SubscriptionResponseDTO;
import com.sage.epicstudent.repository.CourseSubscriptionRepository;
import com.sage.epicstudent.service.manager.CourseManager;
import com.sage.epicstudent.service.manager.CourseSubscriptionManager;
import com.sage.epicstudent.service.manager.StudentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseSubscriptionManagerImpl implements CourseSubscriptionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseSubscriptionManagerImpl.class);

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private CourseSubscriptionRepository courseSubscriptionRepository;

    @Override
    @Transactional
    public SubscriptionResponseDTO handleSubscription(CreateSubscriptionDTO createSubscriptionDTO) {
        LOGGER.info("In handleSubscription");
        SubscriptionResponseDTO subscriptionResponseDTO = new SubscriptionResponseDTO();
        Map<Long, Long> subscriptionToCreate = createSubscriptionDTO.getSubscriptions();
        Map<Long, Student> studentMap = studentManager.fetchStudentsByIds(subscriptionToCreate.keySet());
        Map<Long, Course> courseMap = courseManager.fetchCoursesByIds(new HashSet<>(subscriptionToCreate.values()));
        Course course = null;
        Student student = null;
        for (Entry<Long, Long> studentCourseEntry : subscriptionToCreate.entrySet()) {
            course = courseMap.get(studentCourseEntry.getValue());
            student = studentMap.get(studentCourseEntry.getKey());
            if (Objects.isNull(student) || Objects.isNull(course)) {
                subscriptionResponseDTO.addFailedSubscription(student.getFirstName(), course.getName());
                continue;
            }
            Optional<CourseSubscription> subscriptionOp = courseSubscriptionRepository.findByStudentIdAndCourseId(studentCourseEntry.getKey(), studentCourseEntry.getValue());
            if (subscriptionOp.isPresent()) {
                subscriptionResponseDTO.addExistsSubscription(student.getFirstName(), course.getName());
            } else {
                subscribe(course, student);
                subscriptionResponseDTO.addSuccessfulSubscription(student.getFirstName(), course.getName());
            }
        }
        return subscriptionResponseDTO;
    }

    @Override
    public void subscribe(Course course, Student student) {
        CourseSubscription courseSubscription = new CourseSubscription();
        courseSubscription.setCourse(course);
        courseSubscription.setStudent(student);
        courseSubscription.setType(SubscriptionType.REGULAR);
        //Expiry could be configurable based on user input or subscription type
        courseSubscription.setExpiry(new Date());
        courseSubscriptionRepository.save(courseSubscription);
        LOGGER.info("Student {}, subscribed to course {}", student.getFirstName(), course.getName());
    }

    @Override
    public SubscriptionReport getSubscriptionReport(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<CourseSubscription> subscriptions = courseSubscriptionRepository.findAll(paging);
        return buildSubscriptionReport(subscriptions);
    }

    private SubscriptionReport buildSubscriptionReport(Page<CourseSubscription> subscriptions) {
        SubscriptionReport subscriptionReport = new SubscriptionReport();
        subscriptionReport.setSubscriptions(convertToSubscription(subscriptions.getContent()));
        subscriptionReport.setPageNumber(subscriptions.getPageable().getPageNumber());
        subscriptionReport.setPageSize(subscriptions.getSize());
        subscriptionReport.setTotalPage(subscriptions.getTotalPages());
        subscriptionReport.setTotalElements(subscriptions.getTotalElements());
        return subscriptionReport;
    }

    private List<SubscriptionReport.Subscription> convertToSubscription(List<CourseSubscription> content) {
        List<SubscriptionReport.Subscription> subscriptionList = new LinkedList<>();
        content.stream().forEach(courseSubscription -> {
            SubscriptionReport.Subscription subscription = new SubscriptionReport.Subscription();
            subscription.setStudentName(courseSubscription.getStudent().getFirstName(), courseSubscription.getStudent().getLastName());
            subscription.setCourseName(courseSubscription.getCourse().getName());
            subscriptionList.add(subscription);
        });
        return subscriptionList;
    }


}
