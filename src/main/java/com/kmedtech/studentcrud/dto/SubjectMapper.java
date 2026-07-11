package com.kmedtech.studentcrud.dto;


import com.kmedtech.studentcrud.model.Subject;
import com.kmedtech.studentcrud.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    private static SubjectRepository subjectRepository;

    @Autowired
    public SubjectMapper(SubjectRepository subjectRepository) {
        SubjectMapper.subjectRepository = subjectRepository;
    }

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

    public static Subject toEntityWithDeduplication(SubjectDTO dto) {
        if (dto == null) {
            return null;
        }

        if (dto.getSubjectName() != null && !dto.getSubjectName().isEmpty()) {
            return subjectRepository.findBySubjectName(dto.getSubjectName())
                    .orElseGet(() -> {
                        Subject subject = new Subject();
                        subject.setSubjectName(dto.getSubjectName());
                        return subject;
                    });
        }

        return toEntity(dto);
    }
}
