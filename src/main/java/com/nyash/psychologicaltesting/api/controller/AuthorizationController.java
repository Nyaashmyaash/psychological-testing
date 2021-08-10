package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.exception.NotFoundException;
import com.nyash.psychologicaltesting.api.store.entity.TokenEntity;
import com.nyash.psychologicaltesting.api.store.repository.PsychologistRepository;
import com.nyash.psychologicaltesting.api.store.repository.TokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class AuthorizationController {

    TokenRepository tokenRepository;

    PsychologistRepository psychologistRepository;

    public static final String AUTHORIZE = "/api/psychologists/authorizations";

    @GetMapping(AUTHORIZE)
    public ResponseEntity<String> authorize(
            @RequestParam String login,
            @RequestParam String password) {

        psychologistRepository
                .findTopByLoginAndPassword(login, password)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким логином и паролем не найден."));

        TokenEntity tokenEntity = tokenRepository.saveAndFlush(TokenEntity.builder().build());

        return ResponseEntity.ok(tokenEntity.getToken());
    }
}
