package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AnswerDTO;
import com.nyash.psychologicaltesting.api.dto.QuestionDTO;
import com.nyash.psychologicaltesting.api.dto.TestDTO;
import com.nyash.psychologicaltesting.api.exceptions.BadRequestException;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.TestDTOFactory;
import com.nyash.psychologicaltesting.api.store.entities.AnswerEntity;
import com.nyash.psychologicaltesting.api.store.entities.PsychologistEntity;
import com.nyash.psychologicaltesting.api.store.entities.QuestionEntity;
import com.nyash.psychologicaltesting.api.store.entities.TestEntity;
import com.nyash.psychologicaltesting.api.store.repositories.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class TestController {

    TestRepository testRepository;

    UserRepository userRepository;

    TestUserRepository testUserRepository;

    SchoolClassRepository schoolClassRepository;

    PsychologistRepository psychologistRepository;

    TestDTOFactory testDTOFactory;

    public static final String FETCH_TESTS = "/api/tests";
    public static final String GET_TEST = "/api/tests/{testId}";
    public static final String CREATE_OR_UPDATE_TEST = "/api/tests";
    public static final String DELETE_TEST = "/api/tests/{testId}";
    public static final String COMPLETE_TEST = "/api/schools/classes/{classId}/users/{userId}/tests/{testId}/psychologists/{psychologistId}/compete";

    @GetMapping(FETCH_TESTS)
    public ResponseEntity<List<TestDTO>> fetchTests(@RequestParam(defaultValue = "") String filter) {

        boolean isFiltered = !filter.trim().isEmpty();

        List<TestEntity> tests = testRepository.findAllByFilter(isFiltered, filter);

        return ResponseEntity.ok(testDTOFactory.createTestDTOList(tests));
    }

    @GetMapping(GET_TEST)
    public ResponseEntity<TestDTO> getTest(@PathVariable Long testId) {

        TestEntity test = testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Тест с идентификатором \"%s\" не найден.", testId))
                );

        return ResponseEntity.ok(testDTOFactory.createTestDTO(test));
    }

    @PostMapping(CREATE_OR_UPDATE_TEST)
    public ResponseEntity<TestDTO> createOrUpdateTest(
            @RequestBody TestDTO test) {

        TestEntity testEntity =
    }

    private TestEntity getTestOrThrowNotFound(Long testId) {
        return testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Тест с идентификатором \"%s\" не найден.", testId))
                );
    }

    private TestEntity convertTestToEntity(TestDTO dto) {

        Long testId = dto.getId();

        TestEntity test;
        if(testId == null) {
            test = TestEntity.makeDefault();
        } else {
            test = testRepository
                    .findById(testId)
                    .orElseThrow(() ->
                            new NotFoundException(String.format("Тест с индентификатором \"%s\" не найден.", testId))
                    );
        }

        test.setName(dto.getName());
        test.getQuestions().clear();

        test.getQuestions().addAll(
                dto.getQuestions()
                .stream()
                .map()
                .collect(Collectors.toList())
        );

        return test;
    }

    private QuestionEntity convertQuestionToEntity (QuestionDTO dto) {

        QuestionEntity question = QuestionEntity.makeDefault();

        question.setId(dto.getId());
        question.setQuestionOrder(dto.getOrder());
        question.setText(dto.getText());
        question.getAnswers().clear();

        question.getAnswers().addAll(
                dto.getAnswers()
                .stream()
                .map(this::convertAnswerToEntity)
                .collect(Collectors.toList())
        );

        return question;
    }

    private AnswerEntity convertAnswerToEntity(AnswerDTO dto) {

        AnswerEntity answer = AnswerEntity.makeDefault();

        answer.setId(dto.getId());
        answer.setName(dto.getName());
        answer.setAnswerOrder(dto.getOrder());

        return answer;
    }
}
