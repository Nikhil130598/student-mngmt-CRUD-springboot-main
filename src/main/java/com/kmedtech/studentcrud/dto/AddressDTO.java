package com.kmedtech.studentcrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    @NotBlank(message = "Street can't be blank")
    @Size(min = 3, max = 100, message = "Street must be between 3 and 100 characters")
    private String street;

    @NotBlank(message = "City can't be blank")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "City can only contain letters and spaces")
    private String city;

    @NotBlank(message = "State can't be blank")
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "State can only contain letters and spaces")
    private String state;

    @NotBlank(message = "Zip code can't be blank")
    @Size(min = 5, max = 10, message = "Zip code must be between 5 and 10 characters")
    @Pattern(regexp = "^[0-9\\-]*$", message = "Zip code can only contain numbers and hyphens")
    private String zipCode;
}
