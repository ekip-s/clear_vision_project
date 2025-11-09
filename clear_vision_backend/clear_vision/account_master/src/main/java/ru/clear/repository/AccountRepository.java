package ru.clear.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clear.model.account.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findAllByUserIdOrderByCreatedAt(@Param("userId") UUID userId);
    Optional<Account> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);
}
