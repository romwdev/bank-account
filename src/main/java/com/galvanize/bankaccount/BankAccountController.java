package com.galvanize.bankaccount;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
