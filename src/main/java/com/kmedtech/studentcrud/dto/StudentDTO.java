package com.kmedtech.studentcrud.dto;

import com.kmedtech.studentcrud.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    @NotBlank(message = "firstName can't be blank")
    private String firstName;
    private String lastName;
    @Email
    @NotBlank(message = "Email can't be blank")

    private String email;
    @Min(value=0)
    @Max(value = 120)
    private Integer age;

    private AddressDTO address;

    private List<MarksDTO> marks;

    private List<SubjectDTO> subjects;



     //Default Constructor
    //public StudentDTO() {
   // }

    // Parameterized Constructor
    public StudentDTO(Long id, String firstName, String lastName, String email, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public StudentDTO(Long id, String firstName, String lastName, String email, Integer age, List<MarksDTO> marks) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.marks = marks;
    }

    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//
//    public AddressDTO getAddress(){
//        return address;
//    }
//
//    public void setAddress(AddressDTO address){
//        this.address = address;
//    }
}





