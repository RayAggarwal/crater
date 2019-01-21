package com.crater.api.repository;

import com.crater.api.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    List<VerificationToken> findAllByExpiryTimeAfter(Date date);
}
