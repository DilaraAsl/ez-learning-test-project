package com.ezlearning.platform.controller;

import com.ezlearning.platform.auth.User;
import com.ezlearning.platform.auth.UserRepository;
import com.ezlearning.platform.model.Course;
import com.ezlearning.platform.repositories.CourseRepository;
import com.ezlearning.platform.services.core.impl.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matricula")
@PreAuthorize("hasRole('ROLE_USER')")
public class EnrollmentController {

    private EnrollmentService enrollmentService;
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/save/{id_curso}")
    public String saveMatricula(@PathVariable Long id_curso, Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            enrollmentService.createMatricula(id_curso, username);
            User user = userRepository.findByUsername(username);
            Course course = courseRepository.findById(id_curso).get();
            model.addAttribute("curso", course);
            model.addAttribute("user", user);
            return "matricula-success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
