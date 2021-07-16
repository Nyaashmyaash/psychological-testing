package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.exceptions.BadRequestException;
import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class SchoolController {

    SchoolRepository schoolRepository;

    private static final String CREATE_SCHOOL = "/api/school/{schoolName}";

    @PostMapping(CREATE_SCHOOL)
    public ResponseEntity<?> createSchool(@PathVariable String schoolName) {

        if (schoolRepository.existsByName(schoolName)) {
            throw new BadRequestException
                    (String.format("A school with name \"%s\" already exists", schoolName));
        }

        SchoolEntity school = schoolRepository.saveAndFlush(
                SchoolEntity.makeDefault(schoolName));


    }
}
