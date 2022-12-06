package main.controllers;

import main.modules.accounts.CreditCard;
import main.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {
    @Autowired
    CreditCardRepository creditCardRepository;

    /*@Autowired
    AccountRepository accountRepository;*/

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> findAllCreditCardAccounts() {
        return creditCardRepository.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCardAccount(@RequestBody CreditCard account){
        return creditCardRepository.save(account);
    }

}
