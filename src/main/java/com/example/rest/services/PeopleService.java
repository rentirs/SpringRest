package com.example.rest.services;

import com.example.rest.models.Person;
import com.example.rest.repositories.PeopleRepository;
import com.example.rest.util.PersonNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(int id) {
        return repository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person) {
        personAddInfo(person);
        repository.save(person);
    }

    private void personAddInfo(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedBy("ADMIN");
    }


}
