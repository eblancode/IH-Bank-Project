package main.services.users;

import main.modules.users.AccountHolder;
import main.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    public void deleteById(Long id) {
        accountHolderRepository.deleteById(id);
    }

    public AccountHolder updateAccountHolder(Long id, AccountHolder accountHolder) {
        if (accountHolderRepository.findById(id).isPresent()) {
            accountHolder.setId(id);
            return accountHolderRepository.save(accountHolder);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El id especificado no se encuentra en la base de datos");
    }

    public List<AccountHolder> findAllAccountHolders() {
        return accountHolderRepository.findAll();
    }

    public AccountHolder findAccountHolder(Long id) {
        return accountHolderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder does not exsist"));
    }

    public AccountHolder addAccountHolder(AccountHolder accountHolder) {
        return accountHolderRepository.save(accountHolder);
    }

}
