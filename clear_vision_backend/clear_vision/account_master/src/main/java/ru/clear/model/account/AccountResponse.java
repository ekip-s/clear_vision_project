package ru.clear.model.account;

import account.AccountOuterClass.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private UUID id;
    private AccountType type;
    private String name;
    private BigDecimal balance;
    private AccountCurrency currency;
    private AccountStatus status;
    private LocalDateTime createdAt;
}
