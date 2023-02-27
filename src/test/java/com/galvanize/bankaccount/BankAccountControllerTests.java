package com.galvanize.bankaccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BankAccountService bankAccountService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String path = "/api/bank-account";
    BankAccount account = new BankAccount(12345, "Robert Taylor", "USAA", 2023);

    @Test
    void getRequestWithNoAccountNumberOrParamsReturnsList() throws Exception {
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bankAccounts.add(new BankAccount(11111 + i, "John Doe", "Wells Fargo", 2023));
        }
        when(bankAccountService.getAccounts()).thenReturn(new AccountList(bankAccounts));
        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts", hasSize(5)));
    }

    @Test
    void getRequestWithParamsReturnsList() throws Exception {
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bankAccounts.add(new BankAccount(11111 + i, "John Doe", "Wells Fargo", 2023));
        }
        when(bankAccountService.getAccounts(anyString(), anyInt())).thenReturn(new AccountList(bankAccounts));
        mockMvc.perform(get(path)
                .param("company", "Wells Fargo")
                .param("year", "2023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts", hasSize(5)));
    }

    @Test
    void getRequestNoAccountsFoundReturns204() throws Exception {
        when(bankAccountService.getAccounts()).thenReturn(new AccountList());
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void postRequestReturnsAccount() throws Exception {
        when(bankAccountService.addAccount(any(BankAccount.class))).thenReturn(account);

        mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value(12345));
    }

    @Test
    void postRequestBadContentReturns400() throws Exception {
        when(bankAccountService.addAccount(any(BankAccount.class))).thenThrow(InvalidAccountException.class);

        mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest());
    }
}
