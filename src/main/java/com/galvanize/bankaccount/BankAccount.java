package com.galvanize.bankaccount;

public class BankAccount {
    private long accountNumber;
    private String name;
    private String company;
    private int year;
    private double balance;

    public long getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BankAccount(long accountNumber, String name, String company, int year) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.company = company;
        this.year = year;
    }

    public BankAccount() {
    }
}
