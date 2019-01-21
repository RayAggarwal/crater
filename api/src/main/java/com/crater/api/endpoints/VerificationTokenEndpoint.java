package com.crater.api.endpoints;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class VerificationTokenEndpoint {

    @RequestMapping(path = "/{token}", method = RequestMethod.GET)
    public ResponseEntity activateUser(@PathVariable("token") String token) {

        return ResponseEntity.ok().build();
    }
}
