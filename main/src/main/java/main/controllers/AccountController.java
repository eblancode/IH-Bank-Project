package main.controllers;

import main.modules.accounts.Account;
import main.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

}
