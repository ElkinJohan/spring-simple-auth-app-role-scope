package com.elkindev.subscriber.persistence.repository;

import com.elkindev.subscriber.persistence.entities.ScopeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<ScopeEntity, Long> {
    Optional<ScopeEntity> findByName(String name);
}
