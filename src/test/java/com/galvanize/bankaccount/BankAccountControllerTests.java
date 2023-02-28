package com.galvanize.bankaccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankAccountService bankAccountService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String path = "/api/bank-account";
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(12345L, "Robert Taylor", "USAA", 2023);
    }

    @Test
    void getRequestWithNoAccountNumberOrParamsReturnsList() throws Exception {
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bankAccounts.add(new BankAccount(11111L + i, "John Doe", "Wells Fargo", 2023));
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
            bankAccounts.add(new BankAccount(11111L + i, "John Doe", "Wells Fargo", 2023));
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

    @Test
    void getRequestWithAccountNumberReturnsAccount() throws Exception {
        when(bankAccountService.getAccount(anyLong())).thenReturn(account);

        mockMvc.perform(get(path + '/' + account.getAccountNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(account.getAccountNumber()));
    }

    @Test
    void getRequestWithAccountNumberNotFoundReturns204() throws Exception {
        when(bankAccountService.getAccount(anyLong())).thenReturn(new BankAccount());

        mockMvc.perform(get(path + '/' + account.getAccountNumber()))
                .andExpect(status().isNoContent());
    }

    @Test
    void patchRequestReturnsAccount() throws Exception {
        UpdateAccount update = new UpdateAccount("Bobert", 10.99);

        when(bankAccountService.updateAccount(anyLong(), anyString(), anyDouble()))
                .thenReturn(account);

        mockMvc.perform(patch(path + '/' + account.getAccountNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(account.getName()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }

    @Test
    void patchRequestAccountNotFoundReturns204() throws Exception {
        UpdateAccount update = new UpdateAccount("Bobert", 10.99);

        when(bankAccountService.updateAccount(anyLong(), anyString(), anyDouble()))
                .thenReturn(new BankAccount());

        mockMvc.perform(patch(path + '/' + account.getAccountNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andExpect(status().isNoContent());
    }

    @Test
    void patchRequestBadContentReturns400() throws Exception {
        when(bankAccountService.updateAccount(anyLong(), anyString(), anyDouble()))
                .thenThrow(InvalidAccountException.class);

        mockMvc.perform(patch(path + '/' + account.getAccountNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"string\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteRequestReturns202() throws Exception {
        mockMvc.perform(delete(path + '/' + account.getAccountNumber()))
                .andExpect(status().isAccepted());
        verify(bankAccountService).deleteAccount(anyLong());
    }

    @Test
    void deleteRequestNotFoundReturns204() throws Exception {
        doThrow(new AccountNotFoundException()).when(bankAccountService).deleteAccount(anyLong());
        mockMvc.perform(delete(path + "/00000000"))
                .andExpect(status().isNoContent());
    }
}
