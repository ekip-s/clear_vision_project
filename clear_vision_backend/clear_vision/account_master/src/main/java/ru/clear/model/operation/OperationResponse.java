package ru.clear.model.operation;

import account.AccountOuterClass.AccountCurrency;

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
public class OperationResponse {

    private UUID id;
    private String accountName;
    private AccountCurrency currency;
    private String categoryName;
    private OperationStatus status;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
}
