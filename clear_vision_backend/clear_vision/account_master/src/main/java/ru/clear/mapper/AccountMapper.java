package ru.clear.mapper;

import account.AccountOuterClass;
import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clear.model.account.Account;
import ru.clear.model.account.AccountResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "balance", target = "balance", qualifiedByName = "bigDecimalToString")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "localDateTimeToTimestamp")
    AccountOuterClass.Account toGrpc(Account account);
    List<AccountOuterClass.Account> toGrpcList(List<Account> accounts);
    AccountResponse toResponse(Account account);
    List<AccountResponse> toResponseList(List<Account> accounts);

    @org.mapstruct.Named("bigDecimalToString")
    default String bigDecimalToString(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return value.toPlainString();
    }

    @org.mapstruct.Named("localDateTimeToTimestamp")
    default Timestamp localDateTimeToTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return Timestamp.getDefaultInstance();
        }

        long epochSecond = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        int nanos = dateTime.getNano();

        return Timestamp.newBuilder()
                .setSeconds(epochSecond)
                .setNanos(nanos)
                .build();
    }
}
