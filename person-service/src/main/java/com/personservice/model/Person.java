package com.personservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Person {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
    private int postcode;
    private int age;
    private String job;
    private String email;
    private String phoneno;

}
