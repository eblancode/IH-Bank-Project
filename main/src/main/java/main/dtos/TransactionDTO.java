package main.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class TransactionDTO {
    @NotNull
    private Long accountSenderId;
    @NotNull
    private Long accountReceiverId;
    private String receiverUserName;
    @NotNull
    private BigDecimal amount;

    // Regular constructor
    public TransactionDTO(Long accountSenderId, Long accountReceiverId, String receiverUserName, BigDecimal amount) {
        this.accountSenderId = accountSenderId;
        this.accountReceiverId = accountReceiverId;
        this.receiverUserName = receiverUserName;
        this.amount = amount;
    }

    // Constructor used for third-party transactions
    public TransactionDTO(Long accountSenderId, Long accountReceiverId, BigDecimal amount) {
        this.accountSenderId = accountSenderId;
        this.accountReceiverId = accountReceiverId;
        this.amount = amount;
    }

}
