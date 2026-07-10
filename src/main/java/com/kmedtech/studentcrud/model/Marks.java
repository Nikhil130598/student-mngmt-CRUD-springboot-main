package com.kmedtech.studentcrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_marks")


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long marksId;

    @Column(name = "subjectName", nullable = false)
    private String subject;

    @Column(name = "s_marks", nullable = false)
    private Integer s_marks;


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;


}
