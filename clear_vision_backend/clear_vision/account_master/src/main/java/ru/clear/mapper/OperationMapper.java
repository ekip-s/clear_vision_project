package ru.clear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clear.model.operation.Operation;
import ru.clear.model.operation.OperationResponse;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "account.currency", target = "currency")
    @Mapping(source = "category.name", target = "categoryName")
    OperationResponse toResponse(Operation operation);
}
