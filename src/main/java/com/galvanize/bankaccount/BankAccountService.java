package com.galvanize.bankaccount;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<BankAccount> accounts;
        if (year == null) {
            accounts = bankAccountRepository.findByCompany(company);
        } else if (company == null) {
            accounts = bankAccountRepository.findByYear(year);
        } else {
            accounts = bankAccountRepository.findByCompanyAndYear(company, year);
        }

        return accounts.isEmpty() ? new AccountList() : new AccountList(accounts);
    }

    public BankAccount addAccount(BankAccount account) {
        return bankAccountRepository.save(account);
    }

    public BankAccount getAccount(Long accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).orElse(new BankAccount());
    }

    public BankAccount updateAccount(Long accountNumber, String name, Double balance) {
        Optional<BankAccount> account = bankAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            return new BankAccount();
        }
        account.get().setName(name);
        account.get().setBalance(balance);
        return bankAccountRepository.save(account.get());
    }

    public void deleteAccount(Long accountNumber) {
        Optional<BankAccount> account = bankAccountRepository.findByAccountNumber(accountNumber);

        if (account.isEmpty()) {
            throw new AccountNotFoundException();
        }
        bankAccountRepository.delete(account.get());
    }
}
