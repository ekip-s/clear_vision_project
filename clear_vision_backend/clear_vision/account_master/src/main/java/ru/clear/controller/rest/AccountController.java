package ru.clear.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clear.mapper.AccountMapper;
import ru.clear.model.account.AccountResponse;
import ru.clear.service.account.AccountService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account/api/v1")
@Tag(name="account_controller", description = "Методы для управления счетами")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Operation(
            summary = "Счета пользователя",
            description = "Можно получить мои счета," +
                    " если первое обращение то будет создан дефолтный счет."
    )
    @GetMapping
    public List<AccountResponse> getUserAccounts() {
        log.info("GET: account_controller getUserAccounts");
        return accountMapper.toResponseList(accountService.getUserAccounts());
    }
}
