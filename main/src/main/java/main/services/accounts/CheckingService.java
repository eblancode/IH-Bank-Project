package main.services.accounts;

import main.modules.accounts.Account;
import main.modules.accounts.Checking;
import main.modules.accounts.StudentChecking;
import main.repositories.accounts.CheckingRepository;
import main.repositories.accounts.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CheckingService {
    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    public List<Checking> findAllCheckingAccounts() {
        return checkingRepository.findAll();
    }

    public Account createCheckingAccount(Checking checkingAccount) {
        if(Period.between(checkingAccount.getPrimaryOwner().getBirthDate(),
                LocalDate.now()).getYears() < 24) {
            StudentChecking studentChecking = new StudentChecking(checkingAccount.getBalance(),
                    checkingAccount.getSecretKey(), checkingAccount.getStatus(),
                    checkingAccount.getPrimaryOwner(), checkingAccount.getSecondaryOwner());
            return studentCheckingRepository.save(studentChecking);
        }
        else return checkingRepository.save(checkingAccount);
    }

}
