package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.AnswerDTO;
import com.nyash.psychologicaltesting.api.dto.QuestionDTO;
import com.nyash.psychologicaltesting.api.dto.TestDTO;
import com.nyash.psychologicaltesting.api.exception.BadRequestException;
import com.nyash.psychologicaltesting.api.exception.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.TestDTOFactory;
import com.nyash.psychologicaltesting.api.service.ControllerAuthenticationService;
import com.nyash.psychologicaltesting.api.store.entity.*;
import com.nyash.psychologicaltesting.api.store.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
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

    ControllerAuthenticationService authenticationService;

    public static final String FETCH_TESTS = "/api/tests";
    public static final String GET_TEST = "/api/tests/{testId}";
    public static final String CREATE_OR_UPDATE_TEST = "/api/tests";
    public static final String DELETE_TEST = "/api/tests/{testId}";
    public static final String COMPLETE_TEST = "/api/schools/classes/{classId}/users/{userId}/tests/{testId}/psychologists/{psychologistId}/complete";

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
                        new NotFoundException(String.format("???????? ?? ?????????????????????????????? \"%s\" ???? ????????????.", testId))
                );

        return ResponseEntity.ok(testDTOFactory.createTestDTO(test));
    }

    @PostMapping(CREATE_OR_UPDATE_TEST)
    public ResponseEntity<TestDTO> createOrUpdateTest(
            @RequestBody TestDTO test,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        TestEntity testEntity = convertTestToEntity(test);

        testEntity = testRepository.saveAndFlush(testEntity);

        return ResponseEntity.ok(testDTOFactory.createTestDTO(testEntity));
    }

    @DeleteMapping(DELETE_TEST)
    public ResponseEntity<AckDTO> deleteTest(
            @PathVariable Long testId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        TestEntity test = testRepository
                .findById(testId)
                .orElse(null);

        if (test != null) {
            test.getQuestions().forEach(it -> it.getAnswers().clear());
            test.getQuestions().clear();

            testRepository.delete(test);
        }

        return ResponseEntity.ok(AckDTO.makeDefault(true));
    }

    @PostMapping(COMPLETE_TEST)
    public ResponseEntity<AckDTO> completeTest(
            @PathVariable Long classId,
            @PathVariable Long userId,
            @PathVariable Long testId,
            @RequestParam String answers) {

        TestEntity test = getTestOrThrowNotFound(testId);

        List<String> answerList = Arrays.stream(answers.split(","))
                .filter(it -> !it.trim().isEmpty())
                .collect(Collectors.toList());

        if (answerList.size() != test.getQuestions().size()) {
            throw new BadRequestException("???? ???????????????? ???? ???? ?????? ??????????????.");
        }

        schoolClassRepository
                .findById(classId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("?????????? ?? ?????????????????????????????? \"%s\" ???? ????????????.", classId))
                );

        UserEntity user = userRepository
                .findByIdAndSchoolClassId(userId, classId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("???????????????????????? ?? ?????????????????????????????? \"%s\" ???? ????????????.", userId))
                );

        testUserRepository.saveAndFlush(
                TestUserEntity.builder()
                .answers(answers)
                .user(user)
                .test(test)
                .build()
        );

        return ResponseEntity.ok(AckDTO.makeDefault(true));
    }

    private TestEntity getTestOrThrowNotFound(Long testId) {
        return testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("???????? ?? ?????????????????????????????? \"%s\" ???? ????????????.", testId))
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
                            new NotFoundException(String.format("???????? ?? ???????????????????????????????? \"%s\" ???? ????????????.", testId))
                    );
        }

        test.setName(dto.getName());
        test.getQuestions().clear();

        test.getQuestions().addAll(
                dto.getQuestions()
                .stream()
                .map(this::convertQuestionToEntity)
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
