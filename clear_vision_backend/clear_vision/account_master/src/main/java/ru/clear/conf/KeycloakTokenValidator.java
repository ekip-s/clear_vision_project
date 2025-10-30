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

    public boolean isValid(String token) throws Exception {
        HttpClient client = java.net.http.HttpClient.newHttpClient();

        String body = "token=" + token +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(introspectUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());


        Map<String, Object> jsonMap = objectMapper.readValue(response.body(), Map.class);

        return Boolean.TRUE.equals(jsonMap.get("active"));
    }
}
