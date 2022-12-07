package main.controllers.accounts;

import main.modules.accounts.CreditCard;
import main.services.accounts.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCardAccounts() {
        return creditCardService.findAllCreditCardAccounts();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCardAccount(@RequestBody CreditCard account){
        return creditCardService.addCreditCard(account);
    }

}
