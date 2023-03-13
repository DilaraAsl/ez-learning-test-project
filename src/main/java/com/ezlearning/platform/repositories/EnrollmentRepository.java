package com.ezlearning.platform.repositories;

import com.ezlearning.platform.auth.User;
import com.ezlearning.platform.model.Course;
import com.ezlearning.platform.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByCourse(Course course);
    List<Enrollment> findAllByUsuario(User user);
    Enrollment findByCourseAndUsuario(Course course, User user);
}
