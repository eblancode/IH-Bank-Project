package main.services.accounts;

import main.modules.accounts.Account;
import main.modules.accounts.Checking;
import main.modules.accounts.StudentChecking;
import main.repositories.accounts.AccountRepository;
import main.repositories.accounts.CheckingRepository;
import main.repositories.accounts.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    public List<Checking> findAllCheckingAccounts() {
        return checkingRepository.findAll();
    }

    public Account addCheckingAccount(Checking checkingAccount) {
        if(Period.between(checkingAccount.getPrimaryOwner().getBirthDate(),
                LocalDate.now()).getYears() < 24) {
            StudentChecking studentChecking = new StudentChecking(checkingAccount.getBalance(),
                    checkingAccount.getSecretKey(), checkingAccount.getStatus(),
                    checkingAccount.getPrimaryOwner(), checkingAccount.getSecondaryOwner());
            return studentCheckingRepository.save(studentChecking);
        }
        else return checkingRepository.save(checkingAccount);
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccount(Long id) {
        return (Account) accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist."));
    }


    public void deleteAccount(Long id) {
        accountRepository.deleteAccount(id);
    }

    public Account updateAccount(Long id, Account account) {
        if (accountRepository.findById(id).isPresent()) {
            Account updatedAccount = accountRepository.findById(id).get();
            /*if(blog.getTitle() != null) updatedBlog.setTitle(blog.getTitle());*/
            return accountRepository.save(updatedAccount);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "El id especificado no se encuentra en la base de datos");
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

}
