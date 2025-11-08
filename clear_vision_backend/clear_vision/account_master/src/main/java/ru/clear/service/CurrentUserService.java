package ru.clear.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.clear.conf.UserContext;

import java.util.UUID;

@Service
public class CurrentUserService {

    public UUID getCurrentUserId() {
        String userId = extractUserIdFromSecurity();

        if (userId == null) {
            userId = UserContext.USER_ID_KEY.get();
        }

        if (userId == null) {
            throw new IllegalStateException("User ID not found in context");
        }
        return UUID.fromString(userId);
    }

    public String getCurrentUserLogin() {
        String userLogin = extractUserLoginFromSecurity();

        if (userLogin == null) {
            userLogin = UserContext.USER_LOGIN_KEY.get();
        }

        if (userLogin == null) {
            throw new IllegalStateException("User login not found in context");
        }
        return userLogin;
    }

    public Jwt getJwtToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getCredentials() instanceof Jwt) {
            return (Jwt) auth.getCredentials();
        }
        return null;
    }

    private String extractUserIdFromSecurity() {
        Jwt jwt = getJwtToken();
        if (jwt != null) {
            return jwt.getClaimAsString("sub");
        }
        return null;
    }

    private String extractUserLoginFromSecurity() {
        Jwt jwt = getJwtToken();
        if (jwt != null) {
            return jwt.getClaimAsString("preferred_username");
        }
        return null;
    }
}
