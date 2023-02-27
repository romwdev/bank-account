package com.galvanize.bankaccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTests {
    private BankAccountService bankAccountService;
    @Mock
    private BankAccountRepository bankAccountRepository;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        bankAccountService = new BankAccountService(bankAccountRepository);
        account = new BankAccount(12345, "Robert Taylor", "Discover", 2011);
    }

    @Test
    void getAccountsReturnsList() {
        when(bankAccountRepository.findAll()).thenReturn(Collections.singletonList(account));
        AccountList accounts = bankAccountService.getAccounts();
        assertThat(accounts).isNotNull();
        assertThat(accounts.isEmpty()).isFalse();
    }

    @Test
    void getAccountsWithParamsReturnsList() {
        when(bankAccountRepository.findByCompanyAndYear(anyString(), anyInt()))
                .thenReturn(Collections.singletonList(account));
        AccountList accounts = bankAccountService.getAccounts("Discover", 2011);
        assertThat(accounts).isNotNull();
        assertThat(accounts.isEmpty()).isFalse();
    }
}
