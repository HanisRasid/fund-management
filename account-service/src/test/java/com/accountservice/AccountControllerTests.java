package com.accountservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.accountservice.controller.AccountController;
import com.accountservice.dao.AccountRepository;
import com.accountservice.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AccountController.class)
public class AccountControllerTests {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnNotFoundAccount() throws Exception{
        int id = 1;

        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/account/account{id}", id))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void shouldReturnNotFoundUpdateAccount() throws Exception{
        int id = 1;

        Account updatedAccount = Account.builder()
                                .accountName("accountName")
                                .accountNumber("1234")
                                .accountType("accountType")
                                .balance("balance")
                                .date(Date.from(Instant.now()))
                                .build();
        
        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);

        mockMvc.perform(put("/account/{id}", id).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedAccount)))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void shouldReturnNotFoundDeleteAccount() throws Exception {
        int id =1;

        doNothing().when(accountRepository).deleteById(id);
        mockMvc.perform(delete("/delete/{id}", id))
        .andExpect(status().isNotFound())
        .andDo(print());
    }
}
