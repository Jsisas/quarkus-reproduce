package com.reproduce;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;
import io.smallrye.config.WithParentName;

import java.util.Optional;

@ConfigMapping(prefix = "app")
public interface AppConfig {

    @WithName("external-oauth2")
    ExternalOAuth2 externalOauth2();

    interface ExternalOAuth2 {
        GitHub gitHub();
    }

    interface GitHub {
        @WithDefault("https://github.com")
        String baseUrl();

        @WithDefault("https://api.github.com/user")
        String userInfoUrl();

        @WithParentName
        Optional<Client> client();

        interface Client {
            String clientId();

            String clientSecret();
        }
    }
}
