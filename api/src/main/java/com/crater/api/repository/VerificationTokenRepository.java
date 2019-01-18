package com.crater.api.repository;

import com.crater.api.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link VerificationToken} entity. Proxied by spring data jpa.
 */
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
}
