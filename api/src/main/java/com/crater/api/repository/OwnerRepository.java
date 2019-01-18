package com.crater.api.repository;

import com.crater.api.entity.Owner;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link Owner} entity. Proxied by spring data jpa.
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
