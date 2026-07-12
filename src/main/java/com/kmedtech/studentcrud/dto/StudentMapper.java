package com.kmedtech.studentcrud.dto;

import com.kmedtech.studentcrud.model.Address;
import com.kmedtech.studentcrud.model.Student;

public class StudentMapper {

    // Convert Entity to DTO
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO dto = new StudentDTO();

        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());

        if (student.getAddress() != null) {

            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(student.getAddress().getStreet());
            addressDTO.setCity(student.getAddress().getCity());
            addressDTO.setState(student.getAddress().getState());
            addressDTO.setZipCode(student.getAddress().getZipCode());

            dto.setAddress(addressDTO);

        }

        if (student.getMarks() != null) {
            dto.setMarks(
                    student.getMarks()
                            .stream()
                            .map(MarksMapper::toDTO)
                            .toList()


            );
        }

        if (student.getSubjects() != null) {
            dto.setSubjects(
                    student.getSubjects()
                            .stream()
                            .map(SubjectMapper::toDTO)
                            .toList()


            );
        }

        return dto;

    }


// Convert Entity to DTO
//public static StudentDTO toDTO(Student student) {
        // if (student == null) {
//return null;
        //    }
        //return new StudentDTO(
        //     student.getId(),
        //     student.getFirstName(),
        //      student.getLastName(),
        //     student.getEmail(),
        //     student.getAge()
        //  );
        //  }

        // Convert DTO to Entity
//    public static Student toEntity(StudentDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//        Student student = new Student();
//        student.setId(dto.getId());
//        student.setFirstName(dto.getFirstName());
//        student.setLastName(dto.getLastName());
//        student.setEmail(dto.getEmail());
//        student.setAge(dto.getAge());
//        return student;
//    }
//}

    public static Student toEntity(StudentDTO dto) {
        if (dto == null){
            return null;
    }

        Student student = new Student();
      student.setId(dto.getId());
      student.setFirstName(dto.getFirstName());
       student.setLastName(dto.getLastName());
       student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());

        if(dto.getAddress() != null){
            Address address =  new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setZipCode(dto.getAddress().getZipCode());

            student.setAddress(address);
        }

        if(dto.getMarks() != null){
            student.setMarks(
                    dto.getMarks()
                            .stream()
                            .map(MarksMapper::toEntity)
                            .toList()
            );

            if(student.getMarks() != null){
                student.getMarks().forEach(mark -> mark.setStudent(student));
            }
        }
        if(dto.getSubjects() != null){
            student.setSubjects(
                    dto.getSubjects()
                            .stream()
                            .map(SubjectMapper::toEntityWithDeduplication)
                            .toList()
            );
        }

        return student;


    }
}