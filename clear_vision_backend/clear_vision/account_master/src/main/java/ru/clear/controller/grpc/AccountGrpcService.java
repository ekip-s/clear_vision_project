package ru.clear.controller.grpc;

import account.AccountOuterClass.*;
import account.AccountServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clear.mapper.AccountMapper;
import ru.clear.service.account.AccountService;

@GrpcService
public class AccountGrpcService extends AccountServiceGrpc.AccountServiceImplBase {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountGrpcService(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }


    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<Empty> responseObserver) {

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserAccounts(Empty request, StreamObserver<UserAccountsResponse> responseObserver) {

        UserAccountsResponse.Builder responseBuilder = UserAccountsResponse.newBuilder();
        responseBuilder.addAllAccounts(accountMapper.toGrpcList(accountService.getUserAccounts()));
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
