package com.example.rest.controllers;

import com.example.rest.dto.PersonDTO;
import com.example.rest.models.Person;
import com.example.rest.services.PeopleService;
import com.example.rest.util.PersonErrorResponse;
import com.example.rest.util.PersonNotCreatedException;
import com.example.rest.util.PersonNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Data
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService service;
    private final ModelMapper mapper;

    @GetMapping
    List<PersonDTO> findAll() {
        return service.findAll().stream().map(this::personToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    PersonDTO findById(@PathVariable("id") int id) {
        return personToPersonDTO(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        service.save(oersonDTOToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse errorResponse = new PersonErrorResponse("person not found", LocalDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse errorResponse = new PersonErrorResponse(e.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Person oersonDTOToPerson(PersonDTO personDTO) {
        return mapper.map(personDTO, Person.class);
    }
    private PersonDTO personToPersonDTO(Person person) {
        return mapper.map(person, PersonDTO.class);
    }

}
