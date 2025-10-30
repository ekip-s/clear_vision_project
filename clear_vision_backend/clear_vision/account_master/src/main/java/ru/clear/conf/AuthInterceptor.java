package ru.clear.conf;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@GrpcGlobalServerInterceptor
public class AuthInterceptor implements ServerInterceptor {

    private final KeycloakTokenValidator validator;

    @Autowired
    public AuthInterceptor(KeycloakTokenValidator validator) {
        this.validator = validator;
    }

    private static final Metadata.Key<String> AUTHORIZATION_HEADER =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        String token = headers.get(AUTHORIZATION_HEADER);
        if (token == null || !token.startsWith("Bearer ")) {
            call.close(Status.UNAUTHENTICATED.withDescription("Missing token"), new Metadata());
            return new ServerCall.Listener<>() {};
        }
        token = token.substring("Bearer ".length());

        try {
            if (!validator.isValid(token)) {
                call.close(Status.UNAUTHENTICATED.withDescription("Invalid or expired token"), new Metadata());
                return new ServerCall.Listener<>() {};
            }
            return next.startCall(call, headers);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            call.close(Status.UNAUTHENTICATED.withDescription("Token validation error").withCause(e), new Metadata());
            return new ServerCall.Listener<>() {};
        }
    }
}