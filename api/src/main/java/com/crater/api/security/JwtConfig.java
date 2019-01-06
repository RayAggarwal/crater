package com.crater.api.security;

import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    private String authorizationHeader;

    private String jwtPrefix;

    private String secret;

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public String getJwtPrefix() {
        return jwtPrefix;
    }

    public String getSecret() {
        return secret;
    }

}
