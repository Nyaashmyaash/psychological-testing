package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.PsychologistDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.factory.TestUserDTOFactory;
import com.nyash.psychologicaltesting.api.factory.UserDTOFactory;
import com.nyash.psychologicaltesting.api.store.repositories.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class PsychologistController {

    PsychologistRepository psychologistRepository;

    UserDTOFactory userDTOFactory;

    TestUserDTOFactory testUserDTOFactory;

    UserRepository userRepository;

    TestRepository testRepository;

    TestUserRepository testUserRepository;

    SchoolClassRepository schoolClassRepository;





    public static final String FETCH_PSYCHOLOGISTS = "/api/psychologists";
    public static final String CREATE_PSYCHOLOGIST = "/api/psychologists";
    public static final String DELETE_PSYCHOLOGIST = "/api/psychologists";


    @PostMapping(CREATE_PSYCHOLOGIST)
    public ResponseEntity<SchoolDTO> createPsychologist() {

    }

    @GetMapping(FETCH_PSYCHOLOGISTS)
    public ResponseEntity<List<PsychologistDTO>> fetchPsychologists(@RequestParam String filter) {

    }

    @DeleteMapping(DELETE_PSYCHOLOGIST)
    public ResponseEntity<AckDTO> deletePsychologist(@PathVariable Long psychologistId) {

    }
}

