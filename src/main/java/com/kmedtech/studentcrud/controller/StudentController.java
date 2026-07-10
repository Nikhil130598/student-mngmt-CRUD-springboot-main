package com.kmedtech.studentcrud.controller;

import com.kmedtech.studentcrud.dto.StudentDTO;
import com.kmedtech.studentcrud.model.Student;
import com.kmedtech.studentcrud.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//     fetch students by their first name
//    @GetMapping("/firstName")
//    public List<Student> filterStudentsByFirstName(@RequestParam String firstName) {
//
//       return studentService.getStudentsByFirstName(firstName);
//    }

    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<StudentDTO>>
    getStudentByFirstName(
            @PathVariable String firstName) {

        return ResponseEntity.ok(
                studentService.getStudentsByFirstName(firstName));
    }
//
//    // retrieve students by their last names
//    @GetMapping("/lastName")
//    public List<Student> filterStudentsByLastName(@RequestParam String lastName) {
//
//        return studentService.getStudentsByLastName(lastName);
//    }

    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<StudentDTO>>
    getStudentsByLastName(
            @PathVariable String lastName) {

        return ResponseEntity.ok(
                studentService.getStudentsByLastName(lastName));
    }
//    // retrieve students by their ages
//    @GetMapping("/age")
//    public List<Student> filterStudentsByAge(@RequestParam Integer age) {
//
//        return studentService.getStudentsByAge(age);
//    }

    @GetMapping("/Age/{age}")
    public ResponseEntity<List<StudentDTO>>
    getStudentsByAge(@PathVariable Integer age){

        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

//    // retrieve students by their age conditions
//    @GetMapping("/agefilter")
//    public List<Student> filterStudentsByAgeGreaterThan(@RequestParam Integer age) {
//
//        return studentService.getStudentsByAgeGreaterThan(age);
//    }

    @GetMapping("/age greater than/{age}")
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
   @GetMapping("/Paginated")
   public Page<StudentDTO> getStudents(@RequestParam int page,
                                       @RequestParam int size){
        return studentService.getStudents(page,size);
   }

     //get all students with Pagination

    @GetMapping("/paginated")
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
