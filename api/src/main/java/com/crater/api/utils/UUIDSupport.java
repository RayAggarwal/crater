package com.crater.api.utils;

import java.util.UUID;

public abstract class UUIDSupport {

    public String uuid() {
        return UUID.randomUUID().toString();
    }
}
