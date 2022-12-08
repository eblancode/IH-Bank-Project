package main.controllers.accounts;

import main.modules.accounts.Account;
import main.modules.accounts.Status;
import main.repositories.accounts.AccountRepository;
import main.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    // GET ALL ACCOUNTS
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.findAllAccounts();
    }

    // GET ACCOUNT BY ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountHolder(@PathVariable Long id) {
        return accountService.findAccount(id);
    }

    // GET BALANCE IF ALLOWED
    @GetMapping("/balance/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountHolder(@PathVariable String name) {
        //todo: check if admin or accountholder can retrieve the balance and do so
        //return accountService.findAccount(name);
        return null;
    }

    // DELETE ACCOUNT BY ID
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id) {
        //accountService.deleteById(id);
        accountService.deleteAccount(id);
    }

    // UPDATE ACCOUNT BY ID AND PUT
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account){
        return accountService.updateAccount(id,account);
    }

    // UPDATE ACCOUNT BY REQUESTBODY
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateUser(@RequestBody Account account) {
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

    // MAKE TRANSFER
    @PatchMapping("/transfer/{userName}/{id}/{amount}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateStatus(@PathVariable Long userName, @PathVariable Long id, @PathVariable BigDecimal amount){
        // todo: if "getuser" is owner of "Account" then transfer an amount to an Account found by ID
        // todo: EXTRA if transfer is succesful add Transfer to account list
        if(accountRepository.findById(id).isPresent()){
            Account account = accountRepository.findById(id).get();
//            account.setStatus(status);
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
