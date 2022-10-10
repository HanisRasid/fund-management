package com.accountservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Account {
    
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String accountType;
    
    @Column
    private String accountNumber;

    @Column
    private String accountName;

    @Column
    private String balance;

    @Column Date date;
}
