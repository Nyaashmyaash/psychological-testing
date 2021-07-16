package com.nyash.psychologicaltesting.api.factory;

import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import org.springframework.stereotype.Component;

@Component
public class SchoolDTOFactory {

    public SchoolDTO createSchoolDTO(SchoolEntity entity) {
        return SchoolDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
