package ru.clear.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clear.model.account.Account;
import ru.clear.model.operation.Operation;

import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {

    Page<Operation> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);
    Page<Operation> findAllByUserIdAndAccount(@Param("userId") UUID userId,
                                              @Param("account") Account account,
                                              Pageable pageable);
}
