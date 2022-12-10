package main.services;

import main.dtos.TransactionDTO;
import main.modules.Transaction;
import main.modules.accounts.Account;
import main.modules.users.ThirdParty;
import main.modules.users.User;
import main.repositories.TransactionRepository;
import main.repositories.accounts.AccountRepository;
import main.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class TransactionService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserService userService;

    public Transaction transfer(TransactionDTO transactionDTO, String userName) {
        // Check if sender account exist
        //Account sendingAccount = accountService.findAccount(transactionDTO.getAccountSenderId()); todo: implement in accountservice?
        Account sendingAccount = accountRepository.findById(transactionDTO.getAccountSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sending account not found."));

        // Check if user is authorized by checking if it's either owner of the sender's account or admin
        if(!userName.equals(sendingAccount.getPrimaryOwner().getUserName()) &&
                !userName.equals(sendingAccount.getSecondaryOwner().getUserName()) &&
                !userName.equals("admin"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not authorized.");

        // Check if account has enough funds
        BigDecimal amount = transactionDTO.getAmount();
        if(amount.compareTo(sendingAccount.getBalance()) > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The account doesn't have enough funds.");

        // Check if receiver account exists
        Account receiverAccount = accountRepository.findById(transactionDTO.getAccountReceiverId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Receiver account not found."));

        // Check if the receiver's username is owner of the receiver account
        String receiverUserName = transactionDTO.getReceiverUserName();
        if(receiverUserName.equals(receiverAccount.getPrimaryOwner().getUserName()) ||
                receiverUserName.equals(receiverAccount.getSecondaryOwner().getUserName()))
            return makeTransfer(sendingAccount,receiverAccount,amount);

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Receiver's name does not match the provided parameter.");
    }

    public Transaction thirdPartyTransfer(TransactionDTO transactionDTO, String userName, String hashedKey, String senderAccountSecretKey) {
        User user = userService.findUserByUserName(userName);
        // Check if user is not admin but Third party user and proceed with verification
        if(!userName.equals("admin")) {
            // If it's not admin nor third party - not authorized
            if(!(user instanceof ThirdParty))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not authorized.");
            else if (!((ThirdParty) user).getHashedKey().equals(hashedKey))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Hashed Key is not correct.");
        }

        // Check if sender account exist
        Account sendingAccount = accountRepository.findById(transactionDTO.getAccountSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sending account not found."));

        // Check if the provided secret key matches the sender account secret key
        if(!sendingAccount.getSecretKey().equals(senderAccountSecretKey) && !userName.equals("admin"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sender account secret key is not correct.");

        // Check if account has enough funds
        BigDecimal amount = transactionDTO.getAmount();
        if(amount.compareTo(sendingAccount.getBalance()) > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The account doesn't have enough funds.");

        // Check if receiver account exists
        Account receiverAccount = accountRepository.findById(transactionDTO.getAccountReceiverId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Receiver account not found."));

        return makeTransfer(sendingAccount,receiverAccount,amount);
    }

    private Transaction makeTransfer(Account sendingAccount, Account receiverAccount, BigDecimal amount) {
        // Calculations
        sendingAccount.checkAndSetBalance(sendingAccount.getBalance().subtract(amount));
        receiverAccount.checkAndSetBalance(receiverAccount.getBalance().add(amount));
        // Save changes to the DB
        accountRepository.save(sendingAccount);
        accountRepository.save(receiverAccount);
        //User makerUser = userService.findUser(transactionDTO.get) todo: extra implement field?

        // Save transaction made to the DB
        Transaction transaction = new Transaction(sendingAccount,receiverAccount,amount);
        return transactionRepository.save(transaction);
    }

}
