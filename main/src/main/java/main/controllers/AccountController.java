package main.controllers;

import main.modules.accounts.Account;
import main.modules.accounts.Status;
import main.repositories.AccountRepository;
import main.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.findAllAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountHolder(@PathVariable Long id) {
        return accountService.findAccount(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id) {
        //accountService.deleteById(id);
        accountService.deleteAccount(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account){
        return accountService.updateAccount(id,account);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateUser(@RequestBody Account account) {
        /*if(userService.findUser(user.getId()).isPresent()) {
        }*/
        if(accountRepository.findById(account.getId()).isPresent()) {
            return accountRepository.save(account);
        }
        return null;
    }

    // CHANGE ACCOUNT STATUS
    @PatchMapping("/account-status/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateStatus(@PathVariable Long id, @RequestParam Status status){
        if(accountRepository.findById(id).isPresent()){
            Account account = accountRepository.findById(id).get();
            account.setStatus(status);
            return accountRepository.save(account);
        }
        return null;
    }

}

/*{
    "id": 4,
        "balance": 200.00,
        "secretKey": "secretkey2134",
        "status": "ACTIVE",
        "creationDate": "2022-12-08",
        "primaryOwner": {
    "id": 1,
            "name": "Eduard",
            "birthDate": "1994-07-01",
            "address": {
        "street": "C/ calle, 123",
                "postalCode": "08000",
                "city": "Barcelona",
                "country": "ES"
        },
        "mailingAddress": null
    }
}*/
