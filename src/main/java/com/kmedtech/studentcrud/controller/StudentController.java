package com.kmedtech.studentcrud.controller;

import com.kmedtech.studentcrud.dto.AddressDTO;
import com.kmedtech.studentcrud.dto.MarksDTO;
import com.kmedtech.studentcrud.dto.StudentDTO;
import com.kmedtech.studentcrud.dto.SubjectDTO;
import com.kmedtech.studentcrud.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET all students - GET http://localhost:8080/api/students
  @GetMapping
   public ResponseEntity<List<StudentDTO>> getAllStudents() {
       List<StudentDTO> students = studentService.getAllStudents();
     return ResponseEntity.ok(students);
  }


    @GetMapping("/firstname/{firstName}")

    public ResponseEntity<List<StudentDTO>>
    getStudentByFirstName(
            @PathVariable String firstName) {

        return ResponseEntity.ok(
                studentService.getStudentsByFirstName(firstName));
    }


    @GetMapping("/lastname/{lastName}")

    public ResponseEntity<List<StudentDTO>>
    getStudentsByLastName(
            @PathVariable String lastName) {

        return ResponseEntity.ok(
                studentService.getStudentsByLastName(lastName));
    }


    @GetMapping("/age/{age}")

    public ResponseEntity<List<StudentDTO>>
    getStudentsByAge(@PathVariable Integer age){

        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }



    @GetMapping("/age/greater-than/{age}")

    public ResponseEntity<List<StudentDTO>>
    getStudentsByAgeGreaterThan(@PathVariable Integer age){

        return ResponseEntity.ok(studentService.getStudentsByAgeGreaterThan(age));
    }

    @GetMapping("/{id}/email")

    public ResponseEntity<String> getStudentEmail(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                studentService.getStudentEmail(id));
   }


    // GET student by ID - GET http://localhost:8080/api/students/{id}
    @GetMapping("/{id}")

     public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
       StudentDTO student = studentService.getStudentById(id);
       return ResponseEntity.ok(student);
   }

    //Get students in paginated format
   @GetMapping("/paginated")

   public Page<StudentDTO> getStudents(@RequestParam int page,
                                      @RequestParam int size){
        return studentService.getStudents(page,size);
   }




   @GetMapping("/{studentId}/marks")
   public ResponseEntity<List<MarksDTO>> getStudentMarks(
           @PathVariable Long studentId) {

       return ResponseEntity.ok(
               studentService.getStudentMarks(studentId));
   }

   // GET student marks greater than specified value
   @GetMapping("/{studentId}/marks/greater-than/{minMarks}")
   public ResponseEntity<List<MarksDTO>> getStudentMarksGreaterThan(
           @PathVariable Long studentId,
           @PathVariable Integer minMarks) {
       return ResponseEntity.ok(
               studentService.getStudentMarksGreaterThan(studentId, minMarks));
   }

   // GET student marks less than specified value
   @GetMapping("/{studentId}/marks/less-than/{maxMarks}")
   public ResponseEntity<List<MarksDTO>> getStudentMarksLessThan(
           @PathVariable Long studentId,
           @PathVariable Integer maxMarks) {
       return ResponseEntity.ok(
               studentService.getStudentMarksLessThan(studentId, maxMarks));
   }

   // GET student marks in range
   @GetMapping("/{studentId}/marks/range/{minMarks}/{maxMarks}")
   public ResponseEntity<List<MarksDTO>> getStudentMarksInRange(
           @PathVariable Long studentId,
           @PathVariable Integer minMarks,
           @PathVariable Integer maxMarks) {
       return ResponseEntity.ok(
               studentService.getStudentMarksInRange(studentId, minMarks, maxMarks));
   }

   // GET count of marks for a student
   @GetMapping("/{studentId}/marks/count")
   public ResponseEntity<Long> getStudentMarksCount(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentMarksCount(studentId));
   }

   // GET average marks for a student
   @GetMapping("/{studentId}/marks/average")
   public ResponseEntity<Double> getStudentAverageMarks(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentAverageMarks(studentId));
   }

   // GET highest mark for a student
   @GetMapping("/{studentId}/marks/highest")
   public ResponseEntity<MarksDTO> getStudentHighestMark(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentHighestMark(studentId));
   }

   // GET lowest mark for a student
   @GetMapping("/{studentId}/marks/lowest")
   public ResponseEntity<MarksDTO> getStudentLowestMark(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentLowestMark(studentId));
   }

   // GET student by marks (global - students with marks > specified)
   @GetMapping("/marks/greater-than/{marks}")
   public ResponseEntity<List<StudentDTO>> getStudentsByMarksGreaterThan(
           @PathVariable Integer marks){
       return ResponseEntity.ok(studentService.getStudentsByMarksGreaterThan(marks));
   }

   // GET student marks sorted by marks ascending
   @GetMapping("/{studentId}/marks/sorted/asc")
   public ResponseEntity<List<MarksDTO>> getStudentMarksSortedAsc(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentMarksSortedAsc(studentId));
   }

   // GET student marks sorted by marks descending
   @GetMapping("/{studentId}/marks/sorted/desc")
   public ResponseEntity<List<MarksDTO>> getStudentMarksSortedDesc(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentMarksSortedDesc(studentId));
   }

   // GET student marks with subject starting with letter
   @GetMapping("/{studentId}/marks/subject-starts/{prefix}")
   public ResponseEntity<List<MarksDTO>> getStudentMarksWithSubjectPrefix(
           @PathVariable Long studentId,
           @PathVariable String prefix) {
       return ResponseEntity.ok(
               studentService.getStudentMarksWithSubjectPrefix(studentId, prefix));
   }

   // GET all marks for all students (grouped)
   @GetMapping("/marks/all-students")
   public ResponseEntity<List<MarksDTO>> getAllMarksForAllStudents() {
       return ResponseEntity.ok(studentService.getAllMarksForAllStudents());
   }



   //  ONE-TO-ONE STUDENT-ADDRESS APIS

   // GET address for a student
   @GetMapping("/{studentId}/address")
   public ResponseEntity<AddressDTO> getStudentAddress(@PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getAddressByStudentId(studentId));
   }

   // GET student by address id
   @GetMapping("/address/{addressId}/student")
   public ResponseEntity<StudentDTO> getStudentByAddressId(@PathVariable Long addressId) {
       return ResponseEntity.ok(studentService.getStudentByAddressIdDTO(addressId));
   }

   // GET all addresses
   @GetMapping("/addresses/all")
   public ResponseEntity<List<AddressDTO>> getAllAddresses() {
       return ResponseEntity.ok(studentService.getAllAddressesDTO());
   }

   // ============ MANY-TO-MANY STUDENT-SUBJECT APIS ============

   // GET all subjects for a student
   @GetMapping("/{studentId}/subjects")
   public ResponseEntity<List<SubjectDTO>> getStudentSubjects(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentSubjectsDTO(studentId));
   }

   // GET subject count for a student
   @GetMapping("/{studentId}/subjects/count")
   public ResponseEntity<Long> getStudentSubjectsCount(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentSubjectsCount(studentId));
   }

   // GET subject names for a student
   @GetMapping("/{studentId}/subjects/names")
   public ResponseEntity<List<String>> getStudentSubjectNames(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentSubjectNames(studentId));
   }

   // GET subjects by name pattern for a student
   @GetMapping("/{studentId}/subjects/search/{pattern}")
   public ResponseEntity<List<SubjectDTO>> searchStudentSubjects(
           @PathVariable Long studentId,
           @PathVariable String pattern) {
       return ResponseEntity.ok(studentService.searchStudentSubjects(studentId, pattern));
   }

   // GET subjects sorted by name for a student
   @GetMapping("/{studentId}/subjects/sorted/asc")
   public ResponseEntity<List<SubjectDTO>> getStudentSubjectsSortedAsc(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentSubjectsSortedAsc(studentId));
   }

   // GET subjects sorted by name descending for a student
   @GetMapping("/{studentId}/subjects/sorted/desc")
   public ResponseEntity<List<SubjectDTO>> getStudentSubjectsSortedDesc(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentSubjectsSortedDesc(studentId));
   }

   // GET all unique subjects across all students
   @GetMapping("/subjects/all")
   public ResponseEntity<List<SubjectDTO>> getAllUniqueSubjects() {
       return ResponseEntity.ok(studentService.getAllUniqueSubjectsDTO());
   }

   // GET count of all unique subjects
   @GetMapping("/subjects/count/all")
   public ResponseEntity<Long> getAllSubjectsCount() {
       return ResponseEntity.ok(studentService.getAllSubjectsCount());
   }

   // GET students enrolled in a specific subject
   @GetMapping("/subjects/{subjectId}/students")
   public ResponseEntity<List<StudentDTO>> getStudentsInSubject(
           @PathVariable Long subjectId) {
       return ResponseEntity.ok(studentService.getStudentsInSubjectDTO(subjectId));
   }

   // GET student count for a specific subject
   @GetMapping("/subjects/{subjectId}/students/count")
   public ResponseEntity<Long> getStudentCountInSubject(
           @PathVariable Long subjectId) {
       return ResponseEntity.ok(studentService.getStudentCountInSubject(subjectId));
   }

   // GET subjects by name
   @GetMapping("/subjects/name/{subjectName}")
   public ResponseEntity<SubjectDTO> getSubjectByName(
           @PathVariable String subjectName) {
       return ResponseEntity.ok(studentService.getSubjectByNameDTO(subjectName));
   }

   // GET subject by ID
   @GetMapping("/subjects/{subjectId}")
   public ResponseEntity<SubjectDTO> getSubjectById(
           @PathVariable Long subjectId) {
       return ResponseEntity.ok(studentService.getSubjectByIdDTO(subjectId));
   }

   // GET all subjects with students count
   @GetMapping("/subjects/with-count/all")
   public ResponseEntity<List<java.util.Map<String, Object>>> getAllSubjectsWithStudentCount() {
       return ResponseEntity.ok(studentService.getAllSubjectsWithStudentCount());
   }

   // GET students who have same subjects as given student
   @GetMapping("/{studentId}/subjects/matching-students")
   public ResponseEntity<List<StudentDTO>> getStudentsWithSameSubjects(
           @PathVariable Long studentId) {
       return ResponseEntity.ok(studentService.getStudentsWithSameSubjectsDTO(studentId));
   }

   // GET common subjects between two students
   @GetMapping("/{studentId1}/subjects/common/{studentId2}")
   public ResponseEntity<List<SubjectDTO>> getCommonSubjects(
           @PathVariable Long studentId1,
           @PathVariable Long studentId2) {
       return ResponseEntity.ok(studentService.getCommonSubjectsDTO(studentId1, studentId2));
   }

   // GET subjects starting with specific letter for a student
   @GetMapping("/{studentId}/subjects/starts-with/{letter}")
   public ResponseEntity<List<SubjectDTO>> getSubjectsStartingWithLetter(
           @PathVariable Long studentId,
           @PathVariable String letter) {
       return ResponseEntity.ok(studentService.getSubjectsStartingWithLetter(studentId, letter));
   }

   // GET subjects containing specific text for a student
   @GetMapping("/{studentId}/subjects/contains/{text}")
   public ResponseEntity<List<SubjectDTO>> getSubjectsContainingText(
           @PathVariable Long studentId,
           @PathVariable String text) {
       return ResponseEntity.ok(studentService.getSubjectsContainingText(studentId, text));
   }

    //Get all students with Pagination using Pageable
   @GetMapping("/paginated/all")

   public ResponseEntity<Page<StudentDTO>> getAllStudentsPaginated(Pageable pageable) {
        Page<StudentDTO> students = studentService.getAllStudentsPaginated(pageable);
        return ResponseEntity.ok(students);
   }



    // CREATE a student - POST http://localhost:8080/api/students
    @PostMapping
   public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
       StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
   }





    // UPDATE a student - PUT http://localhost:8080/api/students/{id}
    @PutMapping("/{id}")

    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDetailsDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDetailsDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    // DELETE a student - DELETE http://localhost:8080/api/students/{id}
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }




}
