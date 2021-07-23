package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.store.repositories.PsychologistRepository;
import com.nyash.psychologicaltesting.api.store.repositories.TokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class AuthorizationController {

    TokenRepository tokenRepository;

    PsychologistRepository psychologistRepository;

    public static final String AUTHORIZE = "/api/psychologists/authorizations";
}
