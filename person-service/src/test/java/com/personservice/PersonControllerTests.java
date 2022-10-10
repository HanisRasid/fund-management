package com.personservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.personservice.controller.PersonController;
import com.personservice.dao.PersonRepository;
import com.personservice.model.Person;

@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @MockBean
    private PersonRepository personRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    //Tests if the post request actually creates a person
    @Test
    void shouldCreatePerson() throws Exception {
        
        Person person = Person.builder()
                        .id(1)
                        .name("name")
                        .address("address")
                        .postcode(3029)
                        .age(23)
                        .job("job")
                        .email("email")
                        .phoneno("phoneno")
                        .build();

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(person)))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    //Tests if the get request returns the correct object
    @Test
    void shouldReturnPerson() throws Exception {

        int id = 1;
        Person person = Person.builder()
        .id(1)
        .name("name")
        .address("address")
        .postcode(3029)
        .age(23)
        .job("job")
        .email("email")
        .phoneno("phoneno")
        .build();

        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/persons/person{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(person.getName()))
            .andExpect(jsonPath("$.address").value(person.getAddress()))
            .andExpect(jsonPath("$.postcode").value(person.getPostcode()))
            .andExpect(jsonPath("$.age").value(person.getAge()))
            .andExpect(jsonPath("$.job").value(person.getJob()))
            .andExpect(jsonPath("$.email").value(person.getEmail()))
            .andExpect(jsonPath("$.phoneno").value(person.getPhoneno()))
            .andDo(print());
    }

    //Tests if put request actually updates the person
    @Test
    void shouldUpdatePerson() throws Exception {
      int id = 1;
    
      Person person = Person.builder()
      .id(1)
      .name("name")
      .address("address")
      .postcode(3029)
      .age(23)
      .job("job")
      .email("email")
      .phoneno("phoneno")
      .build();

      Person newPerson = Person.builder()
      .id(1)
      .name("newName")
      .address("newAddress")
      .postcode(3021)
      .age(24)
      .job("newJob")
      .email("newEmail")
      .phoneno("newPhoneno")
      .build();;

      when(personRepository.findById(id)).thenReturn(Optional.of(person));
      when(personRepository.save(any(Person.class))).thenReturn(newPerson);
  
      mockMvc.perform(put("/persons/{id}", id).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(newPerson)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(id))
          .andExpect(jsonPath("$.name").value(newPerson.getName()))
          .andExpect(jsonPath("$.address").value(newPerson.getAddress()))
          .andExpect(jsonPath("$.postcode").value(newPerson.getPostcode()))
          .andExpect(jsonPath("$.age").value(newPerson.getAge()))
          .andExpect(jsonPath("$.job").value(newPerson.getJob()))
          .andExpect(jsonPath("$.email").value(newPerson.getEmail()))
          .andExpect(jsonPath("$.phoneno").value(newPerson.getPhoneno()))
          .andDo(print());
    }
}
