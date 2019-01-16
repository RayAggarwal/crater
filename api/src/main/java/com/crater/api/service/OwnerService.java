package com.crater.api.service;

import com.crater.api.entity.Owner;
import com.crater.api.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OwnerService {

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(PasswordEncoder passwordEncoder, OwnerRepository ownerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public Owner createOwner(Owner owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        owner.setApplicationId(UUID.randomUUID().toString());
        return ownerRepository.save(owner);
    }
}
