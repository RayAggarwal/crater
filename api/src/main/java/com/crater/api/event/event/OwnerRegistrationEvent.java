package com.crater.api.event.event;

public class OwnerRegistrationEvent {

    private final String email;

    private final String token;

    public OwnerRegistrationEvent(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
