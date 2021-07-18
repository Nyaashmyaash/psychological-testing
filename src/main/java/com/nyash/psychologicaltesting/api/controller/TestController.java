package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.store.entities.TestEntity;
import com.nyash.psychologicaltesting.api.store.repositories.TestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class TestController {

    TestRepository testRepository;

    public static final String FETCH_TESTS = "/api/tests";
    public static final String CREATE_OR_UPDATE_TEST = "/api/tests";
    public static final String DELETE_TEST = "/api/tests/{testId}";

//    @GetMapping(FETCH_TESTS)
//    public ResponseEntity<?> fetchTests() {
//
//    }

    @PostMapping(CREATE_OR_UPDATE_TEST)
    public ResponseEntity<?> createOrUpdateTest(
            @RequestParam(required = false) Long testId,
            @RequestParam(required = false) Long psychologistId,
            @RequestParam(required = false) String testName) {

        TestEntity test;

        if (testId != null) {
            test = testRepository
                    .findById(testId)
                    .orElseThrow(() ->
                            new NotFoundException(String.format("Тест с идентификатором \"%s\" не найден.", testId))
                    );
        }

        Optional.ofNullable(testId)
                .map(testRepository::findById)
                .or
    }
}
