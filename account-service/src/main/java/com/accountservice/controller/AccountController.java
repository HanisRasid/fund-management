package com.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accountservice.dao.AccountRepository;
import com.accountservice.model.Account;
import com.accountservice.exception.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class AccountController {
    
    @Autowired
    private AccountRepository repo;

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account acc){
        Account newAcc = repo.save(Account.builder()
            .accountType(acc.getAccountName())
            .accountNumber(acc.getAccountNumber())
            .accountName(acc.getAccountName())
            .balance(acc.getBalance())
            .date(acc.getDate())
            .build());
        
        return new ResponseEntity<Account>(newAcc, HttpStatus.CREATED);
    }

    @GetMapping("/account/account{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id){
        Account accObj = repo.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Not found account with id = " + id));

        return new ResponseEntity<>(accObj, HttpStatus.OK);
    }

    @GetMapping("/accounts/account")
    public ResponseEntity<List<Account>> getAccountList() {
        List<Account> accounts = new ArrayList<Account>();

        repo.findAll().forEach(accounts::add);

        if (accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/account{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable int id) {
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updatePerson(@PathVariable int id, @RequestBody Account account) {

        Account accObj = repo.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Not found person with id = " + id));

            accObj.setAccountType(account.getAccountType());
            accObj.setAccountNumber(account.getAccountNumber());
            accObj.setAccountName(account.getAccountName());
            accObj.setBalance(account.getBalance());
            accObj.setDate(account.getDate());

            return new ResponseEntity<>(repo.save(accObj), HttpStatus.OK);
    }

}
