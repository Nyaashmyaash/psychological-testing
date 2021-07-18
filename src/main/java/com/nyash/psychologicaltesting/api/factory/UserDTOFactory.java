package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.UserDTO;
import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOFactory {

    public UserDTO createUserDTO(UserEntity entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddlename())
                .lastName(entity.getLastName())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .role(entity.getRole())
                .schoolClassId(entity.getSchoolClass().getId())
                .build();
    }

    public List<UserDTO> createUserDTOList(List<UserEntity> entities) {
        return entities
                .stream()
                .map(this::createUserDTO)
                .collect(Collectors.toList());
    }

}
