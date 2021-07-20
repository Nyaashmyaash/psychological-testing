package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.*;
import com.nyash.psychologicaltesting.api.store.entities.AnswerEntity;
import com.nyash.psychologicaltesting.api.store.entities.QuestionEntity;
import com.nyash.psychologicaltesting.api.store.entities.TestEntity;
import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestDTOFactory {

    public LiteTestDTO createLiteTestDTO(TestEntity entity) {
        return LiteTestDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public TestDTO createTestDTO(TestEntity entity) {
        return TestDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isStarted(entity.getIsStarted())
                .build();
    }

    public List<TestDTO> createTestDTOList(List<TestEntity> entities) {
        return entities
                .stream()
                .map(this::createTestDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO createQuestionDTO(QuestionEntity entity) {
        return QuestionDTO.builder()
                .id(entity.getId())
                .order(entity.getQuestionOrder())
                .answers(entity.getAnswers())
                .text()
    }

    public List<QuestionEntity> createQuestionDTOList(List<QuestionEntity> entities) {

    }

    public AnswerDTO createAnswerDTO(AnswerEntity entity) {
        return AnswerDTO.builder()
                .id(entity.getId())
                .order(entity.getAnswerOrder())
                .name(entity.getName())
                .build();
    }

    public List<AnswerDTO> createAnswerDTOList(List<AnswerEntity> entities) {
        return entities
                .stream()
                .map(this::createAnswerDTO)
                .distinct()
                .collect(Collectors.toList());
    }
}
