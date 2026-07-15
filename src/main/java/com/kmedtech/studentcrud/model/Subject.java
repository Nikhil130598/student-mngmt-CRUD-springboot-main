package com.kmedtech.studentcrud.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Entity
@Table(name = "subjects")
@Getter
@Setter

public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


@Column(name = "subject_id", nullable = false)
    private Long subjectId;

@Column(name = "subject_name", nullable = false, unique = true)
    private String subjectName;

    @ManyToMany(mappedBy = "subjects")
    private List<Student> students;

    public Subject() {
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

   //public Long getSubjectId() {
//        return subjectId;
//    }
//
//    public void setSubjectId(Long subjectId) {
//        this.subjectId = subjectId;
//    }
//
//    public String getSubjectName() {
//        return subjectName;
//    }
//
//    public void setSubjectName(String subjectName) {
//        this.subjectName = subjectName;
//    }
//
//
}
