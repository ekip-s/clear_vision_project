package ru.clear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clear.model.operation.operation_category.OperationCategory;

import java.util.UUID;

@Repository
public interface OperationCategoryRepository extends JpaRepository<OperationCategory, UUID> {
}
