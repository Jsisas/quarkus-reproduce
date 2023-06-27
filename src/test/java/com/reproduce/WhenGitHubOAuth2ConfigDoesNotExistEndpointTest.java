package com.reproduce;

import io.quarkus.test.junit.QuarkusTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@RequiredArgsConstructor
class WhenGitHubOAuth2ConfigDoesNotExistEndpointTest {
    @Test
    void testGithubOAuth2ResourceWhenConfigDoesNotExist() {
        given()
                .when().get("/oauth/login/github/authorized")
                .then()
                .statusCode(404);
    }
}
