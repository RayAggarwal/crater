package com.crater.api.endpoints;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractEndpoint {

    public ResponseEntity respondCreated(Object body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
