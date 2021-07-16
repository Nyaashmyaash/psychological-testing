package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.SchoolClassDTO;
import com.nyash.psychologicaltesting.api.store.entities.SchoolClassEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

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
}
