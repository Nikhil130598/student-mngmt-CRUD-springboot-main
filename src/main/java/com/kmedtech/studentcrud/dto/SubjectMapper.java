package com.kmedtech.studentcrud.dto;


import com.kmedtech.studentcrud.model.Subject;

public class SubjectMapper {

    public static SubjectDTO toDTO(Subject subject) {
        if (subject == null) {
            return null;
        }

        SubjectDTO dto = new SubjectDTO();



        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectName(subject.getSubjectName());


        return dto;
    }

    public static Subject toEntity(SubjectDTO dto) {
        if (dto == null) {
            return null;
        }

        Subject subject = new Subject();

        subject.setSubjectId(dto.getSubjectId());
        subject.setSubjectName(dto.getSubjectName());


        return subject;
    }
}
