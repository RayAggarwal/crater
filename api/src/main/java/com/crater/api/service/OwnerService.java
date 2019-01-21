package com.crater.api.service;

import com.crater.api.builder.OwnerRegistrationBuilder;
import com.crater.api.entity.Owner;
import com.crater.api.entity.VerificationToken;
import com.crater.api.event.event.OwnerRegistrationEvent;
import com.crater.api.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Service that contains all business logic related to {@link Owner} entity
 */
@Service
public class OwnerService {

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructor
     * @param passwordEncoder The password encoder. Currently {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}
     * @param ownerRepository The {@link Owner} repository.
     */
    @Autowired
    public OwnerService(PasswordEncoder passwordEncoder, OwnerRepository ownerRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.passwordEncoder = passwordEncoder;
        this.ownerRepository = ownerRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Saves owner entity in database with hashed password and a new application id. This saves triggers the add_owner_schema() postgresql function
     * which adds a new schema for the owner as well as the default owner tables
     * @param owner The {@link Owner} entity to save
     * @return The persisted {@link Owner} entity
     */
    @Transactional
    public Owner createOwner(Owner owner) {
        owner = new OwnerRegistrationBuilder().setUsername(owner.getUsername())
                .setHashedPassword(passwordEncoder.encode(owner.getPassword()))
                .build();
        ownerRepository.save(owner);
        publishRegistrationEvent(owner.getUsername(), owner.getVerificationToken().getToken());
        return owner;
    }

    @Transactional
    public void deleteOwner(Owner owner) {

    }
    private void publishRegistrationEvent(String username, String token) {
        OwnerRegistrationEvent ownerRegistrationEvent = new OwnerRegistrationEvent(username, token);
        applicationEventPublisher.publishEvent(ownerRegistrationEvent);
    }
}
