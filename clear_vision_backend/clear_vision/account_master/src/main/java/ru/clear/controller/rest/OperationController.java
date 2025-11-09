package ru.clear.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.clear.model.operation.AddOperation;
import ru.clear.model.operation.OperationResponse;
import ru.clear.service.operation.OperationService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/operation/api/v1")
@Tag(name="operation_controller", description = "Методы для управления операциями")
public class OperationController {

    private final OperationService operationService;

    @Operation(
            summary = "Создать операцию",
            description = "Создает операцию к счету. Проверяет," +
                    " что счет принадлежит тому, кто обращается"
    )
    @PostMapping("/account/{accountId}")
    public void addNewOperations(@PathVariable UUID accountId, @RequestBody AddOperation addOperation) {
        log.info("POST: operation_controller addNewOperations, {}, {}", accountId, addOperation);
        operationService.addNewOperations(accountId, addOperation);
    }

    @Operation(
            summary = "Все операции пользователя",
            description = "Возвращает все операции пользователя с пагинацией"
    )
    @GetMapping("/all")
    public Page<OperationResponse> getUserOperation(@RequestParam(required = false) UUID accountId,
                                                    @ParameterObject Pageable pageable) {
        log.info("POST: operation_controller getUserOperation, {}, {}", accountId, pageable);
        return operationService.getUserOperation(accountId, pageable);
    }
}
