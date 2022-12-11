package main.controllers.accounts;

import main.dtos.AccountDTO;
import main.modules.accounts.*;
import main.repositories.accounts.AccountRepository;
import main.services.accounts.AccountService;
import main.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    UserService userService;

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

    // GET BALANCE IF ALLOWED (BY ID)
    @GetMapping("/get_balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getAccountBalance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        return accountService.getBalance(userDetails,id);
    }

    // UPDATE BALANCE todo: if admin
    @PatchMapping("/update_balance/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateBalance(@PathVariable Long id, @RequestParam BigDecimal balance){
        Account account = accountService.findAccount(id);
        account.setBalance(balance);
        return accountService.saveAccount(account);
    }

    // DELETE ACCOUNT BY ID TODO: IF ADMIN
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id) {
        //accountService.deleteById(id);
        accountService.deleteAccount(id);
    }

    // UPDATE ACCOUNT BY REQUEST BODY
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateUser(@RequestBody AccountDTO accountDTO) { //todo: check if ok
        Long id = accountDTO.getId();
        Account account = accountService.findAccount(id);
        if(account!=null) {
            return accountService.updateAccount(id,account);
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

    // CHECKING
    @GetMapping("/checking/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAllCheckingAccounts() {
        return accountService.findAllCheckingAccounts();
    }

    @PostMapping("/checking/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody Checking account){
        return accountService.addCheckingAccount(account);
    }

    // CREDIT-CARD
    @GetMapping("/credit-card/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCardAccounts() {
        return accountService.findAllCreditCardAccounts();
    }

    @PostMapping("/credit-card/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCardAccount(@RequestBody CreditCard account){
        return accountService.addCreditCardAccount(account);
    }

    // SAVINGS
    @GetMapping("/savings/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> getAllSavingsAccounts() {
        return accountService.findAllSavingsAccounts();
    }

    @PostMapping("/savings/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createCreditCardAccount(@RequestBody Savings account){
        return accountService.addSavingsAccount(account);
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
