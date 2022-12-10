package main.services.accounts;

import main.modules.accounts.CreditCard;
import main.repositories.accounts.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {
    @Autowired
    CreditCardRepository creditCardRepository;

    public List<CreditCard> findAllCreditCardAccounts() {
        return creditCardRepository.findAll();
    }

    public CreditCard addCreditCard(CreditCard account) {
        return creditCardRepository.save(account);
    }

}
