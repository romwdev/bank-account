package com.galvanize.bankaccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
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

    @Test
    void getAccountsByCompanyReturnsList() {
        when(bankAccountRepository.findByCompany(anyString()))
                .thenReturn(Collections.singletonList(account));
        AccountList accounts = bankAccountService.getAccounts("Discover", null);
        assertThat(accounts).isNotNull();
        assertThat(accounts.isEmpty()).isFalse();
    }

    @Test
    void getAccountsByYearReturnsList() {
        when(bankAccountRepository.findByYear(anyInt()))
                .thenReturn(Collections.singletonList(account));
        AccountList accounts = bankAccountService.getAccounts(null, 2011);
        assertThat(accounts).isNotNull();
        assertThat(accounts.isEmpty()).isFalse();
    }

    @Test
    void addAccountReturnsAccount() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);
        BankAccount serviceAccount = bankAccountService.addAccount(account);
        assertThat(serviceAccount).isNotNull();
        assertThat(serviceAccount.getAccountNumber()).isEqualTo(account.getAccountNumber());
    }

    @Test
    void getAccountByAccountNumberReturnsAccount() {
        when(bankAccountRepository.findByAccountNumber(anyLong())).thenReturn(Optional.ofNullable(account));
        BankAccount serviceAccount = bankAccountService.getAccount(account.getAccountNumber());
        assertThat(serviceAccount).isNotNull();
        assertThat(serviceAccount.getAccountNumber()).isEqualTo(account.getAccountNumber());
    }

    @Test
    void updateAccountReturnsAccount() {
        when(bankAccountRepository.findByAccountNumber(anyLong())).thenReturn(Optional.ofNullable(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(new BankAccount());

        BankAccount serviceAccount = bankAccountService.updateAccount(account.getAccountNumber(),
                "Bobert", 10.99);
        assertThat(account.getName()).isEqualTo("Bobert");
        assertThat(account.getBalance()).isEqualTo(10.99);
    }

    @Test
    void deleteAccountRemovesAccount() {
        when(bankAccountRepository.findByAccountNumber(anyLong())).thenReturn(Optional.ofNullable(account));
        bankAccountService.deleteAccount(account.getAccountNumber());
        verify(bankAccountRepository).delete(any(BankAccount.class));
    }
}
