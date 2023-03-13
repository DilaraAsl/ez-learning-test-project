package com.ezlearning.platform.controller;

import com.ezlearning.platform.auth.User;
import com.ezlearning.platform.auth.UserRepository;
import com.ezlearning.platform.dto.CourseDto;
import com.ezlearning.platform.model.Course;
import com.ezlearning.platform.model.Profesor;
import com.ezlearning.platform.repositories.CourseRepository;
import com.ezlearning.platform.repositories.EnrollmentRepository;
import com.ezlearning.platform.repositories.ProfesorRepository;
import com.ezlearning.platform.services.core.impl.CourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController{

    private final  CourseService courseService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository; // enrollment repository
    private final UserRepository userRepository;
    private final ProfesorRepository profesorRepository;

    public CourseController(CourseService courseService, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository, ProfesorRepository profesorRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.profesorRepository = profesorRepository;
    }


    @GetMapping("/add/{id_profesor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCourse(@PathVariable Long id_profesor, Model model) {
        try {
            Profesor current = profesorRepository.findById(id_profesor).get();
            model.addAttribute("curso", new CourseDto());
            model.addAttribute("profesor", current);
            return "courses/courses-add";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/add/{id_profesor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCurso(@PathVariable Long id_profesor, CourseDto course, Model model) {
        try {
            Profesor current = profesorRepository.findById(id_profesor).get();
            course.setProfesor(current);
            courseService.create(course);
            return "redirect:/courses/courses"; //??
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }

    }

    @GetMapping("/edit/{id_course}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCursoForUpdate(@PathVariable Long id_course, Model model) {
        try {
            Course courseActual = courseRepository.findById(id_course).get();
            model.addAttribute("course", courseActual);
            return "courses/courses-edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/edit/{id_profesor}/{id_course}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateCurso(@PathVariable Long id_profesor, @PathVariable Long id_course, Course course, Model model, RedirectAttributes attributes) {

        try {
            Profesor currentProfesor = profesorRepository.findById(id_profesor).get();
            course.setProfesor(currentProfesor);

            courseService.update(course, id_course);
            attributes.addAttribute("id_course", id_course);

            return "redirect:/courses/{id_course}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping
    public String getCursosList(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "/courses/courses";
    }

    @GetMapping("/delete/{id_course}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCurso(@PathVariable Long id_course, Model model) {
        try {
            Course courseActual = courseRepository.findById(id_course).get();
            courseService.delete(courseActual);

            return "redirect:/courses";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/{id_course}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getCursoDetail(@PathVariable Long id_course, Authentication authentication, Model model) {
        String username = authentication.getName();
        Boolean matriculado = false;
        try {
            Course course = courseRepository.findById(id_course).get();
            User user = userRepository.findByUsername(username);
            if (null != enrollmentRepository.findByCourseAndUsuario(course, user)) {
                matriculado = true;
            }
            model.addAttribute("course", course);
            model.addAttribute("matriculado", matriculado);
            return "/courses/courses-detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
