package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.TestUserDTO;
import com.nyash.psychologicaltesting.api.dto.UserDTO;
import com.nyash.psychologicaltesting.api.exception.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.TestUserDTOFactory;
import com.nyash.psychologicaltesting.api.factory.UserDTOFactory;
import com.nyash.psychologicaltesting.api.service.ControllerAuthenticationService;
import com.nyash.psychologicaltesting.api.store.entity.TestUserEntity;
import com.nyash.psychologicaltesting.api.store.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class PsychologistController {

    UserDTOFactory userDTOFactory;

    TestUserDTOFactory testUserDTOFactory;

    UserRepository userRepository;

    TestRepository testRepository;

    TestUserRepository testUserRepository;

    SchoolClassRepository schoolClassRepository;

    ControllerAuthenticationService authenticationService;

    public static final String GET_TEST_RESULTS = "/api/psychologists/tests/{testId}/results";
    public static final String GET_USERS_BY_CLASS = "/api/psychologists/schools/classes/{classId}";
    public static final String GENERATE_LINK_FOR_TEST = "/api/psychologists/schools/classes/{classId}/tests/{testId}/generate-link";

    public static final String LINK_TEMPLATE = "/api/psychologists";

    @GetMapping(GET_TEST_RESULTS)
    public ResponseEntity<List<TestUserDTO>> getTestResults(
            @PathVariable Long testId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        checkTestById(testId);

        List<TestUserEntity> testUserList = testUserRepository.findAllByTestIdAndPsychologistId(testId);

        return ResponseEntity.ok(testUserDTOFactory.createTestUserDTOList(testUserList));
    }

    @GetMapping(GENERATE_LINK_FOR_TEST)
    public ResponseEntity<String> generateLinkForTest(
            @PathVariable Long classId,
            @PathVariable Long testId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        checkClassById(classId);

        checkTestById(testId);

        return ResponseEntity.ok(String.format(LINK_TEMPLATE, testId, classId));
    }

    @GetMapping(GET_USERS_BY_CLASS)
    public ResponseEntity<List<UserDTO>> getUsersByClass(
            @PathVariable Long classId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

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
                        new NotFoundException(String.format("?????????? ?? ?????????????????????????????? \"%s\" ???? ????????????.", classId)));
    }

    private void checkTestById(Long testId) {
        testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("???????? ?? ?????????????????????????????? \"%s\" ???? ????????????.", testId)));
    }
}

