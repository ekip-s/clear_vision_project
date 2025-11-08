package ru.clear.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
public class KeycloakTokenValidator {

    @Value("${keycloak.realm-url}")
    private String introspectUrl;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenValidationResult validate(String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String body = "token=" + token +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(introspectUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, Object> jsonMap = objectMapper.readValue(response.body(), Map.class);

        boolean isActive = Boolean.TRUE.equals(jsonMap.get("active"));

        if (!isActive) {
            return new TokenValidationResult(false, null, null);
        }

        String userId = (String) jsonMap.get("sub");
        String userLogin = (String) jsonMap.get("preferred_username");

        return new TokenValidationResult(true, userId, userLogin);
    }

    public static class TokenValidationResult {
        private final boolean valid;
        private final String userId;
        private final String userLogin;

        public TokenValidationResult(boolean valid, String userId, String userLogin) {
            this.valid = valid;
            this.userId = userId;
            this.userLogin = userLogin;
        }

        public boolean isValid() {
            return valid;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserLogin() {
            return userLogin;
        }
    }
}
