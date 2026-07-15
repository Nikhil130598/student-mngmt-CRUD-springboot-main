package com.kmedtech.studentcrud.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarksDTO {

    private Long marksId;

    @NotBlank(message = "Subject name can't be blank")
    @Size(min = 2, max = 100, message = "Subject name must be between 2 and 100 characters")
    private String subjectName;

    @NotNull(message = "Marks can't be null")
    @Min(value = 0, message = "Marks must be at least 0")
    @Max(value = 100, message = "Marks must not exceed 100")
    private Integer marks;
}
