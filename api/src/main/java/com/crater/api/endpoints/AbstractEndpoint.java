package com.crater.api.endpoints;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility endpoint class
 * @author Raymond Aggarwal
 */
public abstract class AbstractEndpoint {

    /**
     * Return {@link HttpStatus#CREATED} with response body
     * @param body The response body to send back
     * @return Initialized {@link ResponseEntity} with status 201 and response body
     */
    public ResponseEntity respondCreated(Object body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
