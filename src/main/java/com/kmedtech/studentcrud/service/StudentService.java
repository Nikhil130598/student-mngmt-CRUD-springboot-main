package com.kmedtech.studentcrud.service;

import com.kmedtech.studentcrud.dto.AddressDTO;
import com.kmedtech.studentcrud.dto.MarksDTO;
import com.kmedtech.studentcrud.dto.StudentDTO;
import com.kmedtech.studentcrud.dto.StudentMapper;
import com.kmedtech.studentcrud.dto.SubjectDTO;
import com.kmedtech.studentcrud.model.Address;
import com.kmedtech.studentcrud.model.Marks;
import com.kmedtech.studentcrud.model.Student;
import com.kmedtech.studentcrud.model.Subject;
import com.kmedtech.studentcrud.repository.MarksRepository;
import com.kmedtech.studentcrud.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
public class StudentService {

    private final StudentRepository studentRepository;


    // Constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    // Helper method to find the Student Entity internally
    private Student findStudentEntityById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student with id {} not found", id);
                    return new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id: " + id);
                });
    }

    public Student getStudentsByAddressId(Long addressId) {

        return studentRepository.findByAddressId(addressId)
                .orElseThrow(() -> {
                    log.error("Student with address id {} not found", addressId);
                    return new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with address id: " + addressId);
                });

    }



    public String getStudentEmail(Long id) {

        log.info("Fetching student email with id {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->{
                        log.error("Student with id {} not found", id);
                       return new RuntimeException("Student Not Found");});

        return student.getEmail();
    }



    public List<StudentDTO> getStudentsByFirstName(String firstName) {
        log.info("Fetching students with first name {}", firstName);
        return studentRepository.findByFirstName(firstName)
                .stream()

                .map(StudentMapper::toDTO)
                .toList();

    }



    public List<StudentDTO> getStudentsByLastName(String lastName) {
        log.info("Fetching students with last name {}", lastName);
        return studentRepository.findByLastName(lastName)
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }



    public List<StudentDTO> getStudentsByAge(Integer age) {
        log.info("Fetching students with age {}", age);
        return studentRepository.findByAge(age)
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }


    public List<MarksDTO> getStudentMarks(Long studentId) {
        log.info("Fetching marks for student with id {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        return student.getMarks()
                .stream()
                .map(mark -> {
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    public List<StudentDTO> getStudentsByMarksGreaterThan(Integer marks) {
        log.info("Fetching students with marks greater than {}", marks);

        return studentRepository.findByMarksGreaterThan(marks)
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }



    public List<StudentDTO> getStudentsByAgeGreaterThan(Integer age) {
        log.info("Fetching students with age greater than {}", age);

        return studentRepository.findByAgeGreaterThan(age)
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }

    // Retrieve all students as DTOs
    public List<StudentDTO> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }


    // Retrieve a student by ID as DTO
    public StudentDTO getStudentById(Long id) {
        log.info("Fetching student with id {}", id);
        Student student = findStudentEntityById(id);

        log.info("Student fetched successfully");

        return StudentMapper.toDTO(student);
    }


    // Create a new student from DTO and return DTO
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating new student");
        if (studentRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
            log.error("Student with email {} already exists", studentDTO.getEmail());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already exists: " + studentDTO.getEmail());
        }
        log.info("Creating new student with email {}", studentDTO.getEmail());

        Student student = StudentMapper.toEntity(studentDTO);

        student.setId(null); // Ensure ID is generated by database
        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully");
        return StudentMapper.toDTO(savedStudent);

    }


    // Update an existing student using DTO and return DTO
    public StudentDTO updateStudent(Long id, StudentDTO studentDetailsDTO) {
        log.info("Updating student with id {}", id);
        Student student = findStudentEntityById(id);
        log.info("Found student with id {}", id);

        if (!student.getEmail().equalsIgnoreCase(studentDetailsDTO.getEmail()) &&
                studentRepository.findByEmail(studentDetailsDTO.getEmail()).isPresent())
        {
            log.error("Student with email {} already exists", studentDetailsDTO.getEmail());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already exists: " + studentDetailsDTO.getEmail());
        }

        student.setFirstName(studentDetailsDTO.getFirstName());

        student.setLastName(studentDetailsDTO.getLastName());
        student.setEmail(studentDetailsDTO.getEmail());
        student.setAge(studentDetailsDTO.getAge());

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated successfully");
        return StudentMapper.toDTO(updatedStudent);
    }

    // Delete a student by ID
    public void deleteStudent(Long id) {
        log.info("Deleting student with id {}", id);
        Student student = findStudentEntityById(id);
        studentRepository.delete(student);
        log.info("Student deleted successfully");
    }

    // retrieve students in paginated  and sorted format
    public Page<StudentDTO> getStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("age").ascending());
        return studentRepository.findAll(pageable)
                .map(StudentMapper::toDTO);

    }

    //Pagination - get all students with Pagination
    public Page<StudentDTO> getAllStudentsPaginated(Pageable pageable) {
         return studentRepository.findAll(pageable).map(StudentMapper::toDTO);
    }

    // GET marks - all marks
    public List<Object> getAllMarks() {
        log.info("Fetching all student marks");
        log.info("Found all student marks");
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getMarks().stream())
                .map(Object.class::cast)
                .toList();
    }

    // GET marks by student ID
    public List<Object> getMarksByStudentId(Long studentId) {
        log.info("Fetching marks for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        log.info("Found student with id {}", studentId);
        log.info("Found marks for student with id {}", studentId);
        return student.getMarks().stream()
                .map(Object.class::cast)
                .toList();
    }

    // GET marks by subject
    public List<Object> getMarksBySubject(String subjectName) {
        log.info("Fetching marks for subject {}", subjectName);
        log.info("Found marks for subject {}", subjectName);

        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getMarks().stream())
                .filter(mark -> mark.getSubjectName().equalsIgnoreCase(subjectName))
                .map(Object.class::cast)
                .toList();
    }

    // GET marks by student ID and subject
    public Object getMarksByStudentAndSubject(Long studentId, String subjectName) {
        log.info("Fetching marks for student with id {}", studentId);
        log.info("Fetching marks for subject {}", subjectName);

        Student student = findStudentEntityById(studentId);
        log.info("Found student with id {}", studentId);
        return student.getMarks().stream()
                .filter(mark -> mark.getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElse(null);
    }

    // GET all subjects
    public List<Object> getAllSubjects() {
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .distinct()
                .map(Object.class::cast)
                .toList();
    }

    // GET subject by ID
    public Object getSubjectById(Long subjectId) {
        log.info("Fetching subject with id {}", subjectId);
        log.info("Found subject with id {}", subjectId);
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .filter(subject -> subject.getSubjectId().equals(subjectId))
                .findFirst()
                .orElse(null);
    }

    // GET subjects by student ID
    public List<Object> getSubjectsByStudentId(Long studentId) {
        log.info("Fetching subjects for student with id {}", studentId);
        log.info("Found student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .map(Object.class::cast)
                .toList();
    }

    // ============ ONE-TO-MANY STUDENT-MARKS APIS ============
    



    // GET student marks greater than specified value
    public List<MarksDTO> getStudentMarksGreaterThan(Long studentId, Integer minMarks) {
        log.info("Fetching marks for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        log.info("Found student with id {}", studentId);
        return student.getMarks().stream()
                .filter(mark -> mark.getMarks() > minMarks)
                .map(mark -> {
                    log.info("Found mark for student with id {}: subject={}, marks={}", studentId, mark.getSubjectName(), mark.getMarks());

                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    // GET student marks less than specified value
    public List<MarksDTO> getStudentMarksLessThan(Long studentId, Integer maxMarks) {
        log.info("Fetching marks for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getMarks().stream()
                .filter(mark -> mark.getMarks() < maxMarks)
                .map(mark -> {
                    log.info("Found mark for student with id {}: subject={}, marks={}", studentId, mark.getSubjectName(), mark.getMarks());
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    // GET student marks in range
    public List<MarksDTO> getStudentMarksInRange(Long studentId, Integer minMarks, Integer maxMarks) {
        log.info("Fetching marks for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getMarks().stream()
                .filter(mark -> mark.getMarks() >= minMarks && mark.getMarks() <= maxMarks)
                .map(mark -> {
                    log.info("Found mark for student with id {}: subject={}, marks={}", studentId, mark.getSubjectName(), mark.getMarks());
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    // GET count of marks for a student
    public Long getStudentMarksCount(Long studentId) {
        log.info("Fetching marks count for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return (long) student.getMarks().size();
    }

    // GET average marks for a student
    public Double getStudentAverageMarks(Long studentId) {
        log.info("Fetching average marks for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getMarks().stream()
                .mapToInt(Marks::getMarks)
                .average()
                .orElse(0.0);
    }

    // GET highest mark for a student
    public MarksDTO getStudentHighestMark(Long studentId) {
        log.info("Fetching highest mark for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        Marks highest = student.getMarks().stream()
                .max((m1, m2) -> m1.getMarks().compareTo(m2.getMarks()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No marks found for student: " + studentId));
        
        MarksDTO dto = new MarksDTO();
        dto.setMarksId(highest.getMarksId());
        dto.setSubjectName(highest.getSubjectName());
        dto.setMarks(highest.getMarks());
        return dto;
    }

    // GET lowest mark for a student
    public MarksDTO getStudentLowestMark(Long studentId) {
        log.info("Fetching lowest mark for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        Marks lowest = student.getMarks().stream()
                .min((m1, m2) -> m1.getMarks().compareTo(m2.getMarks()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No marks found for student: " + studentId));
        
        MarksDTO dto = new MarksDTO();
        dto.setMarksId(lowest.getMarksId());
        dto.setSubjectName(lowest.getSubjectName());
        dto.setMarks(lowest.getMarks());
        return dto;
    }

    // GET student marks sorted by marks ascending
    public List<MarksDTO> getStudentMarksSortedAsc(Long studentId) {
        log.info("Fetching student marks sorted by marks ascending for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getMarks().stream()
                .sorted((m1, m2) -> m1.getMarks().compareTo(m2.getMarks()))
                .map(mark -> {
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    // GET student marks sorted by marks descending
    public List<MarksDTO> getStudentMarksSortedDesc(Long studentId) {
        log.info("Fetching student marks sorted by marks descending for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getMarks().stream()
                .sorted((m1, m2) -> m2.getMarks().compareTo(m1.getMarks()))
                .map(mark -> {
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    // GET student marks with subject starting with prefix
    public List<MarksDTO> getStudentMarksWithSubjectPrefix(Long studentId, String prefix) {
        log.info("Fetching student marks with subject starting with prefix '{}' for student with id {}", prefix, studentId);
        Student student = findStudentEntityById(studentId);
        return student.getMarks().stream()
                .filter(mark -> mark.getSubjectName().startsWith(prefix))
                .map(mark -> {
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }

    // GET all marks for all students
    public List<MarksDTO> getAllMarksForAllStudents() {
        log.info("Fetching all marks for all students");
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getMarks().stream())
                .map(mark -> {
                    MarksDTO dto = new MarksDTO();
                    dto.setMarksId(mark.getMarksId());
                    dto.setSubjectName(mark.getSubjectName());
                    dto.setMarks(mark.getMarks());
                    return dto;
                })
                .toList();
    }






    // ============ MANY-TO-MANY STUDENT-SUBJECT METHODS ============

    // GET all subjects for a student
    public List<SubjectDTO> getStudentSubjectsDTO(Long studentId) {
        log.info("Fetching subjects for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // GET subject count for a student
    public Long getStudentSubjectsCount(Long studentId) {
        log.info("Fetching subject count for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return (long) student.getSubjects().size();
    }

    // GET subject names for a student
    public List<String> getStudentSubjectNames(Long studentId) {
        log.info("Fetching subject names for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .map(Subject::getSubjectName)
                .toList();
    }

    // GET subjects by name pattern for a student
    public List<SubjectDTO> searchStudentSubjects(Long studentId, String pattern) {
        log.info("Searching student subjects for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .filter(subject -> subject.getSubjectName().toLowerCase().contains(pattern.toLowerCase()))
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // GET subjects sorted by name ascending for a student
    public List<SubjectDTO> getStudentSubjectsSortedAsc(Long studentId) {
        log.info("Fetching student subjects sorted by name ascending for student with id {}", studentId);
            Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .sorted((s1, s2) -> s1.getSubjectName().compareTo(s2.getSubjectName()))
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // GET subjects sorted by name descending for a student
    public List<SubjectDTO> getStudentSubjectsSortedDesc(Long studentId) {
        log.info("Fetching student subjects sorted by name descending for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .sorted((s1, s2) -> s2.getSubjectName().compareTo(s1.getSubjectName()))
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // GET all unique subjects across all students
    public List<SubjectDTO> getAllUniqueSubjectsDTO() {
        log.info("Fetching all unique subjects across all students");
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .distinct()
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // GET count of all unique subjects
    public Long getAllSubjectsCount() {
        log.info("Fetching count of all unique subjects");
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .distinct()
                .count();
    }

    // GET students enrolled in a specific subject
    public List<StudentDTO> getStudentsInSubjectDTO(Long subjectId) {
        log.info("Fetching students enrolled in subject with id {}", subjectId);
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getSubjects().stream()
                        .anyMatch(subject -> subject.getSubjectId().equals(subjectId)))
                .map(StudentMapper::toDTO)
                .toList();
    }

    // GET student count for a specific subject
    public Long getStudentCountInSubject(Long subjectId) {
        log.info("Fetching student count for subject with id {}", subjectId);
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getSubjects().stream()
                        .anyMatch(subject -> subject.getSubjectId().equals(subjectId)))
                .count();
    }

    // GET subject by name
    public SubjectDTO getSubjectByNameDTO(String subjectName) {
        log.info("Fetching subject by name: {}", subjectName);
        Subject subject = studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .filter(s -> s.getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Subject not found: " + subjectName));
        
        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectName(subject.getSubjectName());
        return dto;
    }

    // GET subject by ID
    public SubjectDTO getSubjectByIdDTO(Long subjectId) {
        log.info("Fetching subject by ID: {}", subjectId);
        Subject subject = studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .filter(s -> s.getSubjectId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Subject not found with ID: " + subjectId));
        
        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectName(subject.getSubjectName());
        return dto;
    }

    // GET all subjects with student count
    public List<Map<String, Object>> getAllSubjectsWithStudentCount() {
        log.info("Fetching all unique subjects with student counts");
        return studentRepository.findAll()
                .stream()
                .flatMap(student -> student.getSubjects().stream())
                .distinct()
                .map(subject -> {

                    Map<String, Object> subjectData = new HashMap<>();
                    long count = studentRepository.findAll()
                            .stream()
                            .filter(s -> s.getSubjects().contains(subject))
                            .count();
                    subjectData.put("subjectId", subject.getSubjectId());
                    subjectData.put("subjectName", subject.getSubjectName());
                    subjectData.put("studentCount", count);
                    return subjectData;
                })
                .toList();
    }

    // GET students who have same subjects as given student
    public List<StudentDTO> getStudentsWithSameSubjectsDTO(Long studentId) {
        log.info("Fetching students with same subjects as student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        List<Subject> studentSubjects = student.getSubjects();
        log.info("Found subjects for student with id {}: {}", studentId, studentSubjects);
        
        return studentRepository.findAll()
                .stream()
                .filter(s -> !s.getId().equals(studentId))
                .filter(s -> s.getSubjects().stream()
                        .anyMatch(studentSubjects::contains))
                .map(StudentMapper::toDTO)
                .toList();
    }

    // GET common subjects between two students
    public List<SubjectDTO> getCommonSubjectsDTO(Long studentId1, Long studentId2) {
        log.info("Fetching common subjects for students with IDs: {} and {}", studentId1, studentId2);
        Student student1 = findStudentEntityById(studentId1);
        Student student2 = findStudentEntityById(studentId2);
        
        List<Subject> commonSubjects = student1.getSubjects().stream()
                .filter(subject -> student2.getSubjects().contains(subject))
                .toList();
        log.info("Found common subjects for students with IDs: {} and {}", studentId1, studentId2);
        
        return commonSubjects.stream()
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })

                .toList();

    }

    // GET subjects starting with specific letter for a student
    public List<SubjectDTO> getSubjectsStartingWithLetter(Long studentId, String letter) {
        log.info("Fetching subjects starting with letter '{}' for student with id {}", letter, studentId);
        Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .filter(subject -> subject.getSubjectName().startsWith(letter))
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // GET subjects containing specific text for a student
    public List<SubjectDTO> getSubjectsContainingText(Long studentId, String text) {
        log.info("Fetching subjects containing text '{}' for student with id {}", text, studentId);
            Student student = findStudentEntityById(studentId);
        return student.getSubjects().stream()
                .filter(subject -> subject.getSubjectName().toLowerCase().contains(text.toLowerCase()))
                .map(subject -> {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectId(subject.getSubjectId());
                    dto.setSubjectName(subject.getSubjectName());
                    return dto;
                })
                .toList();
    }

    // ============ ONE-TO-ONE STUDENT-ADDRESS METHODS ============

    // GET address for a specific student
    public AddressDTO getAddressByStudentId(Long studentId) {
        log.info("Fetching address for student with id {}", studentId);
        Student student = findStudentEntityById(studentId);
        Address address = student.getAddress();
        if (address == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found for student: " + studentId);
        }
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        return dto;
    }

    // GET student by address id
    public StudentDTO getStudentByAddressIdDTO(Long addressId) {
        log.info("Fetching student by address id: {}", addressId);
        Student student = getStudentsByAddressId(addressId);
        return StudentMapper.toDTO(student);
    }

    // GET all addresses
    public List<AddressDTO> getAllAddressesDTO() {
        log.info("Fetching all addresses");
        return studentRepository.findAll()
                .stream()
                .map(Student::getAddress)
                .filter(address -> address != null)
                .map(address -> {
                    AddressDTO dto = new AddressDTO();
                    dto.setId(address.getId());
                    dto.setStreet(address.getStreet());
                    dto.setCity(address.getCity());
                    dto.setState(address.getState());
                    dto.setZipCode(address.getZipCode());
                    return dto;
                })
                .toList();
    }

}

