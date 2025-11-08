package ru.clear.conf;

import io.grpc.Context;

public class UserContext {
    public static final Context.Key<String> USER_ID_KEY = Context.key("user_id");
    public static final Context.Key<String> USER_LOGIN_KEY = Context.key("user_login");
}