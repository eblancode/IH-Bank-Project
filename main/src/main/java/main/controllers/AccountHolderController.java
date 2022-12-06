package main.controllers;

import main.modules.users.AccountHolder;
import main.repositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountholder")
public class AccountHolderController {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAllAccountHolders() {
        return accountHolderRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder findAccountHolder() {

        return null;
        //return accountHolderRepository.findAll();
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder){
        return accountHolderRepository.save(accountHolder);
    }

}
