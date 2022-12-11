package main.controllers;

import main.dtos.TransactionDTO;
import main.modules.Transaction;
import main.repositories.TransactionRepository;
import main.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionService transactionService;

    @PatchMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction makeAccountTransfer(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TransactionDTO transactionDTO){
        // todo: EXTRA if transfer is successful add Transfer to account list
        //if (userDetails.getUsername().equals(transactionDTO.se))
        return transactionService.transfer(transactionDTO,userDetails.getUsername());
    }

    @PatchMapping("/third-party")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction makeThirdPartyAccountTransfer(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String tpHashedKey, @RequestParam String senderAccountSecretKey, @RequestBody TransactionDTO transactionDTO){
        // todo: EXTRA if transfer is successful add Transfer to account list
        return transactionService.thirdPartyTransfer(transactionDTO,userDetails.getUsername(),tpHashedKey,senderAccountSecretKey);
        //localhost:8081/transfer/third-party?tpHashedKey=tp&accSecretKey=tp
        /*{
            "accountSenderId": "3",
            "accountReceiverId": "7",
            "amount": 2
        }*/
    }

}
