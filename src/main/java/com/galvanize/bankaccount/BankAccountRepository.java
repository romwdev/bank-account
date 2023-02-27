package com.galvanize.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    public List<BankAccount> findByCompanyAndYear(String company, Integer year);
    public List<BankAccount> findByCompany(String company);
    public List<BankAccount> findByYear(Integer year);
    public Optional<BankAccount> findByAccountNumber(Long accountNumber);
}
