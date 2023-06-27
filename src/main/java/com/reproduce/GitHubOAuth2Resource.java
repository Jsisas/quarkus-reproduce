package com.reproduce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.Route.HttpMethod;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpServerRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class GitHubOAuth2Resource {

    private final AppConfig config;
    private final ObjectMapper objectMapper;

    @PermitAll
    @Route(path = "/oauth/login/github/authorized", methods = HttpMethod.GET, produces = MediaType.TEXT_HTML)
    public void authorizeGitHub(RoutingExchange rex) {
        HttpServerRequest req = rex.request();
        AppConfig.ExternalOAuth2 authConfig = config.externalOauth2();
        Optional<AppConfig.GitHub> gitHubOpt = authConfig.gitHub();

        gitHubOpt.ifPresentOrElse(
                gh -> respondWithGitHubConfigVariables(rex, gh),
                () -> handleGitHubOAuthNotEnabled(rex, req.absoluteURI()));

    }

    private void respondWithGitHubConfigVariables(RoutingExchange rex, AppConfig.GitHub gitHub) {
        try {
            Map<String, String> responseMap = Map.of(
                    "clientId", gitHub.clientId(),
                    "clientSecret", gitHub.clientSecret(),
                    "baseUrl", gitHub.baseUrl(),
                    "userInfoUrl", gitHub.userInfoUrl()
            );

            String gitHubResponseMapJson = objectMapper.writeValueAsString(responseMap);
            rex.ok(gitHubResponseMapJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGitHubOAuthNotEnabled(RoutingExchange rex, String absoluteURI) {
        log.warn("GET '{}' - GitHub OAuth2 integration not enabled", absoluteURI);
        rex.notFound().end();
    }
}
