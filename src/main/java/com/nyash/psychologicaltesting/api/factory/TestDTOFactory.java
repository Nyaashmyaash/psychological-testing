package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.TestDTO;
import com.nyash.psychologicaltesting.api.dto.UserDTO;
import com.nyash.psychologicaltesting.api.store.entities.TestEntity;
import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestDTOFactory {

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

}
