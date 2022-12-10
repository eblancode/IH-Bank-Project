package main.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter
public class TransactionDTO {
    @NotNull
    private Long accountSenderId;
    @NotNull
    private Long accountReceiverId;
    //@NotNull
    private String receiverUserName;
    @NotNull
    private BigDecimal amount;

    public TransactionDTO(Long accountSenderId, Long accountReceiverId, String receiverUserName, BigDecimal amount) {
        this.accountSenderId = accountSenderId;
        this.accountReceiverId = accountReceiverId;
        this.receiverUserName = receiverUserName;
        this.amount = amount;
    }

}
