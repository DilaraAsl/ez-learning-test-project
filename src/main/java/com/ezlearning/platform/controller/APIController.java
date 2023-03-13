package com.ezlearning.platform.controller;

import com.ezlearning.platform.model.Course;
import com.ezlearning.platform.model.Profesor;
import com.ezlearning.platform.services.core.impl.CourseService;
import com.ezlearning.platform.services.core.impl.ProfesorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    private  final ProfesorService profesorService;
    private  final CourseService courseService;

    public APIController(ProfesorService profesorService, CourseService courseService) {
        this.profesorService = profesorService;
        this.courseService = courseService;
    }


    @GetMapping("/profesores")
    public List<Profesor> getAllProf() {
        return this.profesorService.getAll();
    }

    @GetMapping("/cursos")
    public List<Course> getAllCurso() {
        return this.courseService.getAll();
    }
}
