package ru.clear.model.operation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.clear.model.account.Account;
import ru.clear.model.operation.operation_category.OperationCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations", indexes = {
        @Index(name = "idx_operations_account_id", columnList = "account_id"),
        @Index(name = "idx_operations_account_created_at", columnList = "account_id, created_at DESC")
})
public class Operation {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;
    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;
    @Column(name = "user_id")
    private UUID userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private OperationCategory category;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OperationStatus status;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "description")
    private String description;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Operation(Account account,  AddOperation addOperation, UUID userId) {
        this.account = account;
        this.userId = userId;
        this.category = addOperation.getCategoryId() == null ? null : new OperationCategory(addOperation.getCategoryId());
        this.status = OperationStatus.DONE;
        this.amount = addOperation.getAmount();
        this.description = addOperation.getDescription();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", account=" + account +
                ", category=" + category +
                ", status=" + status +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}