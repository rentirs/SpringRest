

package com.example.rest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should be not empty!")
    @Size(min = 2, max = 30, message = "Name min=2 max=30")
    private String name;
    @Min(value = 10, message = "Age should be not less 10")
    private int age;
    @Email(message = "This field should be *@*.*")
    @NotEmpty(message = "Email should be not empty!")
    private String email;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "created_by")
    @NotEmpty
    private String createdBy;
}
