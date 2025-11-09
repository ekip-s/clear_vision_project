package ru.clear.service.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clear.model.operation.AddOperation;
import ru.clear.model.operation.OperationResponse;

import java.util.UUID;

public interface OperationService {

    void addNewOperations(UUID accountId, AddOperation addOperation);
    Page<OperationResponse> getUserOperation(UUID accountId, Pageable pageable);
}
