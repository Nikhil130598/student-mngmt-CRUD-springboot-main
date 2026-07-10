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
    private String subject;

    @Min(value=35)
    @Max(value = 100)
    private Integer s_marks;


    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private StudentDTO studentdto;

    public MarksDTO(Long marksId, String subject, Integer s_marks) {
        this.marksId = marksId;
        this.subject = subject;
        this.s_marks = s_marks;

    }

    public MarksDTO() {

    }
}
