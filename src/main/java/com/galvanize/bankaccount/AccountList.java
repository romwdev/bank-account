package com.galvanize.bankaccount;

import java.util.ArrayList;
import java.util.List;

public class AccountList {
    private List<BankAccount> accounts;

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public AccountList(List<BankAccount> accounts) {
        this.accounts = new ArrayList<>(accounts);
    }

    public AccountList() {
        this.accounts = new ArrayList<>();
    }

    public boolean isEmpty() {
        return accounts.isEmpty();
    }
}
