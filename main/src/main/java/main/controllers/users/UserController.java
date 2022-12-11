package main.controllers.users;

import main.modules.users.AccountHolder;
import main.modules.users.Admin;
import main.modules.users.ThirdParty;
import main.services.users.AccountHolderService;
import main.services.users.AdminService;
import main.services.users.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AdminService adminService;
    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    ThirdPartyService thirdPartyService;

    // ADMIN USER
    @GetMapping("/admin/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> getAllAdmins() {
        return adminService.findAllAdmins();
    }

    @GetMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Admin getAdmin(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    // ACCOUNT HOLDER USER
    @GetMapping("/account_holder/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAllAccountHolders() {
        return accountHolderService.findAllAccountHolders();
    }

    @GetMapping("/account_holder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolder(@PathVariable Long id) {
        return accountHolderService.findAccountHolder(id);
    }

    @PostMapping("/account_holder/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder) {
        return accountHolderService.addAccountHolder(accountHolder);
    }

    @DeleteMapping("/account_holder/delete/{id}") // extra (done in account)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccountHolder(@PathVariable Long id) {
        accountHolderService.deleteById(id);
    }

    @PutMapping("/account_holder/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder updateAccountHolder(@PathVariable Long id, @RequestBody AccountHolder accountHolder){
        return accountHolderService.updateAccountHolder(id,accountHolder);
    }

    // THIRD PARTY USER
    @PostMapping("/third-party/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdPartyUser(@RequestBody ThirdParty thirdParty) {
        return thirdPartyService.addThirdParty(thirdParty);
    }

}
