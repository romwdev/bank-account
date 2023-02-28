package com.galvanize.bankaccount;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "account_number")
    private Long accountNumber;
    private String name;
    private String company;
    @Column(name = "year_opened")
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

    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", name=" + name + '\'' +
                ", company=" + company + '\'' +
                ", year=" + year + '\'' +
                ", balance=" + balance + '\'' +
                "}";
    }


    public BankAccount(Long accountNumber, String name, String company, Integer year) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.company = company;
        this.year = year;
    }

    public BankAccount() {
    }
}
