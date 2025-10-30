package ru.clear.model.recurrent;

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
@Table(name = "recurring_operations", indexes = {
        @Index(name = "idx_recurring_operations_account_id", columnList = "account_id"),
        @Index(name = "idx_recurring_operations_next_run", columnList = "next_run_date")
})
public class Recurrent {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;
    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private OperationCategory category;
    @Column(name = "repeat_cron")
    private String repeatCron;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "next_run_date")
    private LocalDateTime nextRunDate;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private Boolean isActive = true;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recurrent recurrent = (Recurrent) o;
        return Objects.equals(id, recurrent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Recurrent{" +
                "id=" + id +
                ", account=" + account +
                ", category=" + category +
                ", repeatCron='" + repeatCron + '\'' +
                ", amount=" + amount +
                ", nextRunDate=" + nextRunDate +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", endDate=" + endDate +
                '}';
    }
}