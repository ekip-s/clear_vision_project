package ru.clear.service.account;

import account.AccountOuterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clear.NotFoundException;
import ru.clear.model.account.Account;
import ru.clear.repository.AccountRepository;
import ru.clear.service.CurrentUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CurrentUserService currentUserService;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    @Transactional
    public List<Account> getUserAccounts() {
        List<Account> accounts = accountRepository.findAllByUserIdOrderByCreatedAt(currentUserService.getCurrentUserId());

        if (accounts.isEmpty()) {
            accounts.add(createInitAccount());
        }

        return accounts;
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.findByIdAndUserId(accountId, currentUserService.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("Такого счета нет", "Нет данных"));
    }

    @Override
    @Transactional
    public Account setBalance(UUID accountId, BigDecimal opsAmount) {
        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance().add(opsAmount));
        return accountRepository.save(account);
    }

    private Account createInitAccount() {
        Account account = new Account(currentUserService.getCurrentUserId(),
                AccountOuterClass.AccountType.CURRENT_ACCOUNT,
                "Основной счёт",
                new BigDecimal("0.00"),
                AccountOuterClass.AccountCurrency.RUB,
                AccountOuterClass.AccountStatus.NEW);

        return accountRepository.save(account);
    }
}
