package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.PsychologistDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
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





    public static final String GET_TEST_RESULTS = "/api/psychologists/tests/{testId}/results";
    public static final String GET_USERS_BY_CLASS = "/api/psychologists/schools/classes/{classId}";
    public static final String GENERATE_LINK_FOR_TEST = "/api/psychologists/schools/classes/{classId}/tests/{testId}/generate-link";

    public static final String LINK_TEMPLATE = "/api/psychologists";


    @GetMapping(GET_TEST_RESULTS)
    public ResponseEntity<List<?>> getTestResults() {

    }

    @GetMapping(GENERATE_LINK_FOR_TEST)
    public ResponseEntity<List<PsychologistDTO>> generateLinkForTest(@RequestParam String filter) {

    }

    @DeleteMapping(GET_USERS_BY_CLASS)
    public ResponseEntity<AckDTO> getUsersByClass(@PathVariable Long psychologistId) {

    }

    private void checkClassById (Long classId) {
        schoolClassRepository
                .findById(classId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Класс с идентификатором \"%s\" не найден.", classId)));
    }
}

