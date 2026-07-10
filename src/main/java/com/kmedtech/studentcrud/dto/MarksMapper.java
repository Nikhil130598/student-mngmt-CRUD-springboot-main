package com.kmedtech.studentcrud.dto;

import com.kmedtech.studentcrud.model.Marks;



public class MarksMapper {

    public static MarksDTO toDto(Marks marks) {
        if (marks == null) {
            return null;
        }

        MarksDTO dto = new MarksDTO();



        dto.setMarksId(marks.getMarksId());
        dto.setSubject(marks.getSubject());
        dto.setS_marks(marks.getS_marks());

        return dto;
    }

    public static Marks toEntity(MarksDTO dto) {
        if (dto == null) {
            return null;
        }

        Marks marks = new Marks();

        marks.setMarksId(dto.getMarksId());
        marks.setSubject(dto.getSubject());
        marks.setS_marks(dto.getS_marks());

        return marks;
    }
}
