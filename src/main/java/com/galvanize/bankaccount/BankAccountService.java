package com.galvanize.bankaccount;

import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
    public AccountList getAccounts() {
        return null;
    }

    public AccountList getAccounts(String company, int year) {
        return null;
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
