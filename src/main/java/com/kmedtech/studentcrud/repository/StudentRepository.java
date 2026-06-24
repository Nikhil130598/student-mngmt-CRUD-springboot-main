package com.kmedtech.studentcrud.repository;

import com.kmedtech.studentcrud.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // We can also define custom query methods here if needed, for example:
    Optional<Student> findByEmail(String email);
    List<Student> findByFirstName(String firstName);
    List<Student> findByLastName(String lastName);
    List<Student> findByAge(Integer age);
    List<Student> findByAgeGreaterThan(Integer age);


}
