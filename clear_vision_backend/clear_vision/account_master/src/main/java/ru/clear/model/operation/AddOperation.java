package ru.clear.model.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOperation {

    private UUID categoryId;
    private BigDecimal amount;
    private String description;

    @Override
    public String toString() {
        return "AddOperation{" +
                "categoryId=" + categoryId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
