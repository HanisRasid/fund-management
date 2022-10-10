package com.personservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Person {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private int postcode;
    @Column
    private int age;
    @Column
    private String job;
    @Column
    private String email;
    @Column
    private String phoneno;

}
