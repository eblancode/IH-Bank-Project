package main.services.accounts;

import main.modules.accounts.Account;
import main.repositories.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccount(Long id) {
        return (Account) accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exsist"));
    }

    /*public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }*/

    public void deleteAccount(Long id) {
//        accountRepository.deleteById(id);
//        System.out.println(accountRepository.findById(id).get().toString());
//        accountRepository.delete(accountRepository.findById(id).get());
        accountRepository.deleteAccount(id);
    }

    public Account updateAccount(Long id, Account account) {
        if (accountRepository.findById(id).isPresent()) {
            Account updatedAccount = accountRepository.findById(id).get();
            /*if(blog.getTitle() != null) updatedBlog.setTitle(blog.getTitle());
            if(blog.getAuthor() != null) updatedBlog.setAuthor(blog.getAuthor());
            if(blog.getPost() != null) updatedBlog.setPost(blog.getPost());*/

            return accountRepository.save(updatedAccount);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "El id especificado no se encuentra en la base de datos");
    }

}
