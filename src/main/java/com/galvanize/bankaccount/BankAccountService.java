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

    public AccountList getAccounts(String company, Integer year) {
        AccountList accounts;
        if (year == null) {
            accounts = new AccountList(bankAccountRepository.findByCompany(company));
        } else if (company == null) {
            accounts = new AccountList(bankAccountRepository.findByYear(year));
        } else {
            accounts = new AccountList(bankAccountRepository.findByCompanyAndYear(company, year));
        }

        return accounts;
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
