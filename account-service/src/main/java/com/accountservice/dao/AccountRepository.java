package com.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountservice.model.Account;
@Repository
public interface AccountRepository  extends JpaRepository<Account,Integer>{
    
}
