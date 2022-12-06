package main.dtos;

import java.math.BigDecimal;

public class TransactionDTO {
    private BigDecimal amount;
    private Long accountSenderId;
    private String name;
    private Long accountReceiverId;
}
