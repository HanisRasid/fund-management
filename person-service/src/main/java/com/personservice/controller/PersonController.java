package com.personservice.controller;

import com.personservice.dao.PersonRepository;
import com.personservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }
    @GetMapping("/persons/person{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id){
        try{
            Person personObj = getPersonInfo(id);
            return new ResponseEntity<>(personObj, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/persons/person")
    public ResponseEntity<List<Person>> getPersonList() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable int id) {
        try {

            Person psn = getPersonInfo(id);

            if (psn != null) {
                repo.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {

        Person personObj = getPersonInfo(id);

        if (personObj != null) {
            personObj.setName(person.getName());
            personObj.setAddress(person.getAddress());
            personObj.setJob(person.getJob());
            personObj.setPostcode(person.getPostcode());
            personObj.setEmail(person.getEmail());
            personObj.setPhoneno(person.getPhoneno());
            personObj.setAge((person.getAge()));
            return new ResponseEntity<>(repo.save(personObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Person getPersonInfo(int id){
        Optional<Person> personObj = repo.findById(id);

        if (personObj.isPresent()){
            return personObj.get();
        }
        return null;
    }
}
