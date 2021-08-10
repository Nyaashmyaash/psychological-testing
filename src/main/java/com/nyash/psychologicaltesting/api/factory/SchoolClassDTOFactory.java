package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.SchoolClassDTO;
import com.nyash.psychologicaltesting.api.store.entity.SchoolClassEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SchoolClassDTOFactory {

    SchoolDTOFactory schoolDTOFactory;

    public SchoolClassDTO createSchoolClassDTO(SchoolClassEntity entity) {
        return SchoolClassDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .school(schoolDTOFactory.createSchoolDTO(entity.getSchool()))
                .build();
    }

    public List<SchoolClassDTO> createSchoolClassDTOList(List<SchoolClassEntity> entities) {
        return entities
                .stream()
                .map(this::createSchoolClassDTO)
                .collect(Collectors.toList());
    }
}
