package main.services;

import main.modules.users.AccountHolder;
import main.modules.users.User;
import main.repositories.AccountHolderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AdminService {
    AccountHolderRepository accountHolderRepository;

    public List<User> getAllUsers(String userName) {
        AccountHolder accountHolder = accountHolderRepository.findByName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return null;
    }

}
