package com.nyash.psychologicaltesting.api.service;

import com.nyash.psychologicaltesting.api.exception.UnauthorizedException;
import com.nyash.psychologicaltesting.api.store.entity.TokenEntity;
import com.nyash.psychologicaltesting.api.store.repository.TokenRepository;
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
public class ControllerAuthenticationService {

    TokenRepository tokenRepository;

    public void authenticate(String tokenStr) {

        TokenEntity token = tokenRepository
                .findById(tokenStr)
                .orElseThrow(() -> new UnauthorizedException("Для выполнения этого действия необходимо войти в систему")
                );

        if (token.getExpiredAt().isBefore(Instant.now())) {
            throw new UnauthorizedException("Время сессии истекло, войдите в систему");
        }
    }
}
