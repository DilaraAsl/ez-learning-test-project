package com.ezlearning.platform.services.core.impl;

import com.ezlearning.platform.auth.User;
import com.ezlearning.platform.auth.UserRepository;
import com.ezlearning.platform.model.Course;
import com.ezlearning.platform.model.Enrollment
        ;
import com.ezlearning.platform.repositories.CourseRepository;
import com.ezlearning.platform.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public void createMatricula(Long id_curso, String username) throws Exception {
        Course course = courseRepository.findById(id_curso).get();
        User user = userRepository.findByUsername(username);

        if (null != enrollmentRepository.findByCourseAndUsuario(course, user)) {
            throw new Exception("Ya se encuentra matriculado en este course");
        }
        LocalDate date = LocalDate.now();
        Enrollment enrollment = new Enrollment(date, user, course);
        enrollmentRepository.save(enrollment);
    }
}
