package main.controllers.users;

import main.modules.users.AccountHolder;
import main.modules.users.User;
import main.repositories.users.AccountHolderRepository;
import main.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountholder")
public class AccountHolderController {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAllAccountHolders() {
        return accountHolderService.findAllAccountHolders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolder(@PathVariable Long id) {
        return accountHolderService.findAccountHolder(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) //todo: if admin
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder) {
        return accountHolderService.addAccountHolder(accountHolder);
//        return accountHolderRepository.save(accountHolder);
    }

    @DeleteMapping("/delete/{id}") // extra (done in account)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccountHolder(@PathVariable Long id) {
        accountHolderService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder updateAccountHolder(@PathVariable Long id,@RequestBody AccountHolder accountHolder){
        return accountHolderService.updateAccountHolder(id,accountHolder);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody AccountHolder accountHolder) {
        /*if(userService.findUser(user.getId()).isPresent()) {}*/
        if(accountHolderRepository.findById(accountHolder.getId()).isPresent()) {
            return accountHolderRepository.save(accountHolder);
        }
        return null;
    }

}
