package com.ezlearning.platform.model;

import com.ezlearning.platform.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @Column(name = "enrollment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_matricula;

    @Column(name = "date")
    private LocalDate fecha_matricula;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Course course;

    public Enrollment(LocalDate fecha_matricula, User usuario, Course course) {
        this.fecha_matricula = fecha_matricula;
        this.usuario = usuario;
        this.course = course;
    }
}
