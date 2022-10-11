package com.personservice.controller;

import com.personservice.dao.PersonRepository;
import com.personservice.exception.PersonNotFoundException;
import com.personservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    private PersonRepository repo;

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        Person newPerson = repo.save(Person.builder()
            .name(person.getName())
            .address(person.getAddress())
            .postcode(person.getPostcode())
            .age(person.getAge())
            .job(person.getJob())
            .email(person.getEmail())
            .phoneno(person.getPhoneno())
            .build());
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }
    @GetMapping("/persons/person{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id){

        Person personObj = repo.findById(id)
            .orElseThrow(() -> new PersonNotFoundException("Not found person with id = " + id));

        return new ResponseEntity<>(personObj, HttpStatus.OK);
    }

    @GetMapping("/persons/person")
    public ResponseEntity<List<Person>> getPersonList() {
        List<Person> persons = new ArrayList<Person>();

        repo.findAll().forEach(persons::add);

        if (persons.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable int id) {
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {

        Person personObj = repo.findById(id)
            .orElseThrow(() -> new PersonNotFoundException("Not found person with id = " + id));

        personObj.setName(person.getName());
        personObj.setAddress(person.getAddress());
        personObj.setJob(person.getJob());
        personObj.setPostcode(person.getPostcode());
        personObj.setEmail(person.getEmail());
        personObj.setPhoneno(person.getPhoneno());
        personObj.setAge((person.getAge()));

        return new ResponseEntity<>(repo.save(personObj), HttpStatus.OK);
    }
}
