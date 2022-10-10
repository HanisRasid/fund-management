package com.personservice.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonEntity extends JpaRepository<Person,Integer> {
}
