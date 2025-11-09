package ru.clear.service.account;

import ru.clear.model.account.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<Account> getUserAccounts();
    Account getAccountById(UUID accountId);
    Account setBalance(UUID accountId, BigDecimal opsAmount);
}
