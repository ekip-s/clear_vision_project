package ru.clear.service;

import account.AccountOuterClass;
import account.AccountServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clear.repository.AccountRepository;

@GrpcService
public class AccountServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(AccountOuterClass.CreateAccountRequest request, StreamObserver<Empty> responseObserver) {

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserAccounts(AccountOuterClass.GetUserAccountsRequest request, StreamObserver<AccountOuterClass.UserAccountsResponse> responseObserver) {

        AccountOuterClass.UserAccountsResponse.Builder responseBuilder = AccountOuterClass.UserAccountsResponse.newBuilder();

        responseBuilder.addAccounts(AccountOuterClass.Account.newBuilder()
                .setAccountId(1)
                .setAccountType("sdsd")
                .setBalance(15.00)
                .build());

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
