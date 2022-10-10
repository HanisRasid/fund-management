package com.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountservice.dao.AccountRepository;
import com.accountservice.model.Account;

import java.util.List;
import java.util.Optional;

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
        try{
            Account accountObj = getAccountInfo(id);

            if (accountObj != null){
                
                return new ResponseEntity<>(accountObj, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/persons/person")
    public ResponseEntity<List<Account>> getAccountList() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable int id) {
        try {

            Account acc = getAccountInfo(id);

            if (acc != null) {
                repo.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updatePerson(@PathVariable int id, @RequestBody Account account) {

        Account accountObj = getAccountInfo(id);

        if (accountObj != null) {
            accountObj.setAccountType(account.getAccountType());
            accountObj.setAccountNumber(account.getAccountNumber());
            accountObj.setAccountName(account.getAccountName());
            accountObj.setBalance(account.getBalance());
            accountObj.setDate(account.getDate());
            return new ResponseEntity<>(repo.save(accountObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Account getAccountInfo(int id){
        Optional<Account> accountObj = repo.findById(id);

        return accountObj.orElse(null);
    }
}
