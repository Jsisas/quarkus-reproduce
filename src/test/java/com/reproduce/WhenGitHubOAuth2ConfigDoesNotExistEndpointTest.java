package com.reproduce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@RequiredArgsConstructor
class WhenGitHubOAuth2ConfigDoesNotExistEndpointTest {

    private final AppConfig config;
    private final ObjectMapper objectMapper;

    @Test
    void testGithubOAuth2ResourceWhenConfigDoesNotExist() throws JsonProcessingException {
        AppConfig.GitHub gitHubConfig = config.externalOauth2().gitHub().get();

        Map<String, String> responseMap = Map.of(
                    "clientId", gitHubConfig.clientId(),
                    "clientSecret", gitHubConfig.clientSecret(),
                    "baseUrl", gitHubConfig.baseUrl(),
                    "userInfoUrl", gitHubConfig.userInfoUrl()
            );

        given()
                .when().get("/oauth/login/github/authorized")
                .then()
                .statusCode(200)
                .body(is(objectMapper.writeValueAsString(responseMap)));
    }

}
