package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.TestUserDTO;
import com.nyash.psychologicaltesting.api.store.entities.TestUserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class TestUserDTOFactory {

    TestDTOFactory testDTOFactory;

    UserDTOFactory userDTOFactory;

    public List<TestUserDTO> createTestUserDTOList(List<TestUserEntity> entities) {
        return entities.stream().map(this::createTestUserDTO).collect(Collectors.toList());
    }

    public TestUserDTO createTestUserDTO(TestUserEntity entity) {
        return TestUserDTO.builder()
                .test(testDTOFactory.createLiteTestDTO(entity.getTest()))
                .user(userDTOFactory.createUserDTO(entity.getUser()))
                .answers(entity.getAnswers())
                .build();
    }
}
