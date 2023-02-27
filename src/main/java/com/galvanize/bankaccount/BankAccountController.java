package com.galvanize.bankaccount;

import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {
    BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping()
    public ResponseEntity<AccountList> getAccounts(@RequestParam(required = false) String company,
                                                   @RequestParam(required = false) Integer year) {
        AccountList accounts;
        if (company == null && year == null) {
            accounts = bankAccountService.getAccounts();
        } else {
            accounts = bankAccountService.getAccounts(company, year);
        }
        return accounts.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(accounts);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccount addAccount(@RequestBody BankAccount account) {
        if (account.getAccountNumber() == null || account.getName() == null ||
                account.getCompany() == null || account.getYear() == null) {
            throw new InvalidAccountException();
        }
        return bankAccountService.addAccount(account);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccountByNumber(@PathVariable Long accountNumber) {
        BankAccount account = bankAccountService.getAccount(accountNumber);
        return account.getAccountNumber() == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(account);
    }

    @PatchMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> updateAccount(@PathVariable Long accountNumber,
                                     @RequestBody UpdateAccount update) {
        BankAccount account = bankAccountService.updateAccount(accountNumber, update.getName(), update.getBalance());

        if (account.getAccountNumber() == null) {
            return ResponseEntity.noContent().build();
        }
        account.setName(update.getName());
        account.setBalance(update.getBalance());

        return ResponseEntity.ok(account);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAccountExceptionHandler(InvalidAccountException e) {}
}
