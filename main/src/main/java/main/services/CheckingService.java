package main.services;

import main.modules.accounts.Checking;
import main.repositories.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckingService {
    @Autowired
    CheckingRepository checkingRepository;

    public List<Checking> findAllCheckingAccounts() {
        return checkingRepository.findAll();
    }



}
