package com.nyash.psychologicaltesting.api.store.repositories;

import com.nyash.psychologicaltesting.api.store.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface TokenRepository extends JpaRepository<TokenEntity, String> {
    void deleteAllByExpiredAtBefore(Instant currentDate);
}
