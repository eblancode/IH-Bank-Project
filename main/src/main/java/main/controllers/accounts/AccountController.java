package main.controllers.accounts;

import main.dtos.AccountDTO;
import main.modules.accounts.Account;
import main.modules.accounts.Checking;
import main.modules.accounts.CreditCard;
import main.modules.accounts.Status;
import main.repositories.accounts.AccountRepository;
import main.services.accounts.AccountService;
import main.services.accounts.CreditCardService;
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
    @Autowired
    CreditCardService creditCardService;

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
    /*@GetMapping("/get_balance/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountHolder(@PathVariable String name) { // todo: auth?
        //todo: check if admin or accountholder can retrieve the balance and do so
        //return accountService.findAccount(name);
        return null;
    }*/

    // GET BALANCE IF ALLOWED (BY ID)
    @GetMapping("/get_balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getBalance(@PathVariable Long id) { // todo: auth? CHECK!
        Account account = accountService.findAccount(id);
        return account.getBalance().toString();
    }

    // UPDATE BALANCE todo: if admin
    @PatchMapping("/update_balance/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateBalance(@PathVariable Long id, @RequestParam BigDecimal balance){
        Account account = accountService.findAccount(id);
        if(account!=null){
            account.setBalance(balance); //todo: do check penalty fee?
            return accountService.saveAccount(account);
        }
        return null;
    }

    // DELETE ACCOUNT BY ID TODO: IF ADMIN
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id) {
        //accountService.deleteById(id);
        accountService.deleteAccount(id);
    }

    // UPDATE ACCOUNT BY ID AND PUT TODO: IF ADMIN
    /*@PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account){
        return accountService.updateAccount(id,account);
    }*/

    // UPDATE ACCOUNT BY REQUEST BODY
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateUser(@RequestBody AccountDTO accountDTO) { //todo: check if ok
        Long id = accountDTO.getId();
        Account account = accountService.findAccount(id);
        if(account!=null/*accountRepository.findById(account.getId()).isPresent()*/) {
            return accountService.updateAccount(id,account);
//            return accountService.saveAccount(account);
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

    // MAKE TRANSFER TODO: IF
    /*@PatchMapping("/transfer")*//*//*transfer/{userName}/{id}/{amount}*//**//*@PathVariable Long userName*//*
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateStatus(@AuthenticationPrincipal UserDetails, @RequestBody TransactionDTO transactionDTO){
        // todo: if "getuser" is owner of "Account" then transfer an amount to an Account found by ID
        // todo: EXTRA if transfer is succesful add Transfer to account list

        if(accountRepository.findById().isPresent()){
            Account account = accountRepository.findById(id).get();
            //account.setStatus(status);
            return accountRepository.save(account);
        }
        return null;
    }*/

    // CHECKING
    @GetMapping("/checking/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAllCheckingAccounts() {
        return accountService.findAllCheckingAccounts();
    }

    @PostMapping("/checking/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody Checking account){
        return accountService.createCheckingAccount(account);
    }

    // CREDIT-CARD
    @GetMapping("/credit-card/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCardAccounts() {
        return creditCardService.findAllCreditCardAccounts();
    }

    @PostMapping("/credit-card/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCardAccount(@RequestBody CreditCard account){
        return creditCardService.addCreditCard(account);
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
