package com.galvanize.bankaccount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {
    BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping()
    public AccountList getAccounts(@RequestParam String company,
                                   @RequestParam int year) {
        if (company == null || year == 0) {
            return bankAccountService.getAccounts();
        }
        return bankAccountService.getAccounts(company, year);
    }
}
