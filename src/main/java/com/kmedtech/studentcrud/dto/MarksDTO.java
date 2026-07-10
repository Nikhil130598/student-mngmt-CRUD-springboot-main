package com.kmedtech.studentcrud.dto;


import com.kmedtech.studentcrud.model.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MarksDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long marksId;

    @NotBlank(message = "subject name can't be blank")
    private String subjectName;

    @Min(value=35)
    @Max(value = 100)
    private Integer marks;



}
