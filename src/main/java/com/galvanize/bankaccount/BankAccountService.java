package com.galvanize.bankaccount;

import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
    BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public AccountList getAccounts() {
        return new AccountList(bankAccountRepository.findAll());
    }

    public AccountList getAccounts(String company, int year) {
        return new AccountList(bankAccountRepository.findByCompanyAndYear(company, year));
    }

    public BankAccount addAccount(BankAccount account) {
        return null;
    }

    public BankAccount getAccount(Long accountNumber) {
        return null;
    }

    public BankAccount updateAccount(Long accountNumber, String name, Double balance) {
        return null;
    }

    public void deleteAccount(Long accountNumber) {

    }
}
