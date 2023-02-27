package com.galvanize.bankaccount;

public class BankAccount {
    private Long accountNumber;
    private String name;
    private String company;
    private Integer year;
    private Double balance;

    public Long getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(Long accountNumber) {
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
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
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
