package com.kmedtech.studentcrud.dto;

import com.kmedtech.studentcrud.model.Student;

public class StudentMapper {

    // Convert Entity to DTO
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge()
        );
    }

    // Convert DTO to Entity
    public static Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        return student;
    }
}
