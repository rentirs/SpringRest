package com.example.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonDTO {
    @NotEmpty(message = "Name should be not empty!")
    @Size(min = 2, max = 30, message = "Name min=2 max=30")
    private String name;
    @Min(value = 10, message = "Age should be not less 10")
    private int age;
    @Email(message = "This field should be *@*.*")
    @NotEmpty(message = "Email should be not empty!")
    private String email;
}
