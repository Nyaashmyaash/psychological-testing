package com.nyash.psychologicaltesting.api.service;

import com.nyash.psychologicaltesting.api.store.repositories.TokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
@Transactional
public class TokenCleaner {

    TokenRepository tokenRepository;

    public void clean() {
        tokenRepository.deleteAllByExpiredAtBefore(Instant.now());
    }
}
