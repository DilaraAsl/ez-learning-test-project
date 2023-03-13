package com.ezlearning.platform.services.core.impl;

import com.ezlearning.platform.dto.CourseDto;
import com.ezlearning.platform.model.Course;
import com.ezlearning.platform.model.Profesor;
import com.ezlearning.platform.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void create(CourseDto courseDto) throws Exception{
        if (null != courseRepository.findByNomCurso(courseDto.getNomCurso())) {
            throw new Exception("Ya existe un course con el nombre " + courseDto.getNomCurso());
        }
        String nomCurso = courseDto.getNomCurso();
        String descCurso = courseDto.getDescCurso();
        String detalleCurso = courseDto.getDetalle();
        String difCurso = courseDto.getDificultad();
        String urlCurso = courseDto.getUrl();
        String imgurl = courseDto.getImgurl();
        Profesor profesor = courseDto.getProfesor();
        Course course = new Course(nomCurso, descCurso, detalleCurso, difCurso, urlCurso, imgurl, profesor);

        courseRepository.save(course);
    }

    public void update(Course course, Long id_curso) {
        Course currentCourse = courseRepository.findById(id_curso).get();

            currentCourse.setNomCurso(course.getNomCurso());
            currentCourse.setDescripcionCurso(course.getDescripcionCurso());
            currentCourse.setDetalleCurso(course.getDetalleCurso());
            currentCourse.setDificultadCurso(course.getDificultadCurso());
            currentCourse.setUrlCurso(course.getUrlCurso());
            currentCourse.setImgurl(course.getImgurl());
            currentCourse.setProfesor(course.getProfesor());

            courseRepository.save(currentCourse);

    }

    public void delete(Course course) { courseRepository.delete(course); }


    public List<Course> getAll() {
        return courseRepository.findAll();
    }

}
