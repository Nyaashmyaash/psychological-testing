package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.TestUserDTO;
import com.nyash.psychologicaltesting.api.dto.UserDTO;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.TestUserDTOFactory;
import com.nyash.psychologicaltesting.api.factory.UserDTOFactory;
import com.nyash.psychologicaltesting.api.store.entities.TestUserEntity;
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
    public ResponseEntity<List<TestUserDTO>> getTestResults(
            @PathVariable Long testId) {

        checkTestById(testId);

        List<TestUserEntity> testUserList = testUserRepository.findAllByTestIdAndPsychologistId(testId);

        return ResponseEntity.ok(testUserDTOFactory.createTestUserDTOList(testUserList));
    }

    @GetMapping(GENERATE_LINK_FOR_TEST)
    public ResponseEntity<String> generateLinkForTest(
            @PathVariable Long classId,
            @PathVariable Long testId) {

        checkClassById(classId);

        checkTestById(testId);

        return ResponseEntity.ok(String.format(LINK_TEMPLATE, testId, classId));
    }

    @DeleteMapping(GET_USERS_BY_CLASS)
    public ResponseEntity<List<UserDTO>> getUsersByClass(
            @PathVariable Long classId) {

        checkClassById(classId);

        List<UserDTO> users = userDTOFactory.createUserDTOList(
                userRepository.findAllByFilterAndClass(false, "", classId)
        );

        return ResponseEntity.ok(users);
    }

    private void checkClassById (Long classId) {
        schoolClassRepository
                .findById(classId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Класс с идентификатором \"%s\" не найден.", classId)));
    }

    private void checkTestById(Long testId) {
        testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Тест с идентификатором \"%s\" не найден.", testId)));
    }
}

