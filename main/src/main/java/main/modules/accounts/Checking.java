package main.modules.accounts;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Checking {
    private BigDecimal balance;

    private BigDecimal minimumBalance;
    private BigDecimal penaltyFee;
    private BigDecimal monthlyMaintenanceFee;
    private LocalDate creationDate;
    private CheckingStatus status;

}
