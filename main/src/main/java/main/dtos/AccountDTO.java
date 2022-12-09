package main.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.accounts.Status;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class AccountDTO {
    private Long id;
    private BigDecimal balance;
    private String secretKey;
    private Status status;

}
