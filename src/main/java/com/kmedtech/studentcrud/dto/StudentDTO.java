package com.kmedtech.studentcrud.dto;

import com.kmedtech.studentcrud.model.Address;

public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    private AddressDTO address;

    // Default Constructor
   // public StudentDTO() {
   // }

    // Parameterized Constructor
  //  public StudentDTO(Long id, String firstName, String lastName, String email, Integer age) {
  //      this.id = id;
  //      this.firstName = firstName;
   //     this.lastName = lastName;
   //     this.email = email;
     //   this.age = age;
  //  }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public AddressDTO getAddress(){
        return address;
    }

    public void setAddress(AddressDTO address){
        this.address = address;
    }
}





