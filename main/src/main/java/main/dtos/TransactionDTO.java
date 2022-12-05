package main.dtos;

import java.math.BigDecimal;

public class TransactionDTO {
    private BigDecimal amount;
    private Long accountSenderId;
    private String name;
    private Long accountReceiverId;

    public TransactionDTO(BigDecimal amount, Long accountSenderId, String name, Long accountReceiverId) {
        this.amount = amount;
        this.accountSenderId = accountSenderId;
        this.name = name;
        this.accountReceiverId = accountReceiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountSenderId() {
        return accountSenderId;
    }

    public void setAccountSenderId(Long accountSenderId) {
        this.accountSenderId = accountSenderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountReceiverId() {
        return accountReceiverId;
    }

    public void setAccountReceiverId(Long accountReceiverId) {
        this.accountReceiverId = accountReceiverId;
    }
}
