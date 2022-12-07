package main.controllers;

import main.modules.accounts.Checking;
import main.repositories.CheckingRepository;
import main.services.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checking")
public class CheckingController {
    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    CheckingService checkingService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> findAllCheckingAccounts() {
        return checkingService.findAllCheckingAccounts();
//        return checkingRepository.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking createCheckingAccount(@RequestBody Checking account){
        return checkingRepository.save(account);
    }

}
