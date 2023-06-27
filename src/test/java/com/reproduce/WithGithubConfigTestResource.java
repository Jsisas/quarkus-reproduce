package com.reproduce;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

public class WithGithubConfigTestResource implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        Map<String, String> map = new HashMap<>();
        map.put("app.external-oauth2.git-hub.client-id", "git-hub-client-id");
        map.put("app.external-oauth2.git-hub.client-secret", "git-hub-client-secret");
        return map;
    }

    @Override
    public void stop() {
    }

}
