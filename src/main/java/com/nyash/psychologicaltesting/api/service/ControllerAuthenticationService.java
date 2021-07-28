package com.nyash.psychologicaltesting.api.service;

import com.nyash.psychologicaltesting.api.exceptions.UnauthorizedException;
import com.nyash.psychologicaltesting.api.store.repositories.TokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class ControllerAuthenticationService {

    TokenRepository tokenRepository;

    public void authenticate(String token) {

        tokenRepository.findById(token)
                .orElseThrow(() -> new UnauthorizedException("Для выполнения этого действия необходимо войти в систему")
                );
    }
}
