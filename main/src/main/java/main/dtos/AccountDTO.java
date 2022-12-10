package main.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.accounts.Status;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class AccountDTO {
    @NotNull
    private Long id;
    private BigDecimal balance;
    private String secretKey;
    private Status status;

}
