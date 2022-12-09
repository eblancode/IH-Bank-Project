package main.controllers;

import main.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transfer")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @PatchMapping("/{userName}/{id}/{amount}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BigDecimal makeTransfer(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable Long userName,
                                @PathVariable Long destinationAccountId,
                                @PathVariable BigDecimal amount){
        // todo: if "getuser" is owner of "Account" then transfer an amount to an Account found by ID
        // todo: EXTRA if transfer is succesful add Transfer to account list

        /*if(accountRepository.findById(id).isPresent()){
            Account account = accountRepository.findById(id).get();
            //account.setStatus(status);
            return accountRepository.save(account);
        }*/
        return null;
    }

}
