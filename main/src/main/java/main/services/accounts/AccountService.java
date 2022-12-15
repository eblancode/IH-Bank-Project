package main.services.accounts;

import main.modules.accounts.*;
import main.repositories.accounts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    SavingsRepository savingsRepository;

    // ACCOUNTS
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccount(Long id) {
        return (Account) accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist"));
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

    // CHECKING ACCOUNTS
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

    // CREDIT-CARD ACCOUNTS
    public List<CreditCard> findAllCreditCardAccounts() {
        return creditCardRepository.findAll();
    }

    public CreditCard addCreditCardAccount(CreditCard account) {
        return creditCardRepository.save(account);
    }

    // SAVING ACCOUNTS
    public List<Savings> findAllSavingsAccounts() {
        return savingsRepository.findAll();
    }

    public String getBalance(UserDetails userDetails, Long id) {
        Account account = this.findAccount(id);
        String userName = userDetails.getUsername();

        // Check if user is authorized
        if(!userName.equals(account.getPrimaryOwner().getUserName()) &&
                !userName.equals(account.getSecondaryOwner().getUserName()) &&
                !userName.equals("admin"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not authorized");


        return account.getBalance().toString();
    }

    public Savings addSavingsAccount(Savings account) {
        return savingsRepository.save(account);
    }

}
