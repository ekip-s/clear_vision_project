package ru.clear.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clear.mapper.OperationMapper;
import ru.clear.model.account.Account;
import ru.clear.model.operation.AddOperation;
import ru.clear.model.operation.Operation;
import ru.clear.model.operation.OperationResponse;
import ru.clear.repository.OperationRepository;
import ru.clear.service.CurrentUserService;
import ru.clear.service.account.AccountService;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final CurrentUserService currentUserService;
    private final OperationMapper operationMapper;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository, AccountService accountService, CurrentUserService currentUserService, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
        this.currentUserService = currentUserService;
        this.operationMapper = operationMapper;
    }

    @Override
    @Transactional
    public void addNewOperations(UUID accountId, AddOperation addOperation) {
        Account account = accountService.setBalance(accountId, addOperation.getAmount());
        Operation operation = new Operation(account, addOperation, currentUserService.getCurrentUserId());
        operationRepository.save(operation);
    }

    @Override
    public Page<OperationResponse> getUserOperation(UUID accountId, Pageable pageable) {

        if (accountId != null) {
            Account accountProxy = new Account(accountId);
            return operationRepository

                    .findAllByUserIdAndAccount(currentUserService.getCurrentUserId(), accountProxy, pageable)
                    .map(operationMapper::toResponse);
        }

        return operationRepository.findAllByUserId(currentUserService.getCurrentUserId(), pageable)
                .map(operationMapper::toResponse);
    }
}
