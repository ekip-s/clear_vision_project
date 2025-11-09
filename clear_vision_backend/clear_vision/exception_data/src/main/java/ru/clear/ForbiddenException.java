package ru.clear;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForbiddenException extends RuntimeException {

    private final String reason;
    private final LocalDateTime timestamp;

    public ForbiddenException(String message, String reason) {
        super(message);
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }
}