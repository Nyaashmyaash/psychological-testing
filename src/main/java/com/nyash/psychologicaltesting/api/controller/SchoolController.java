package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.exceptions.BadRequestException;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.SchoolDTOFactory;
import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class SchoolController {

    SchoolRepository schoolRepository;

    SchoolDTOFactory schoolDTOFactory;

    private static final String FETCH_SCHOOL = "/api/school";
    private static final String CREATE_SCHOOL = "/api/school/{schoolName}";
    private static final String DELETE_SCHOOL = "/api/school/{schoolId}";


    @PostMapping(CREATE_SCHOOL)
    public ResponseEntity<SchoolDTO> createSchool(@PathVariable String schoolName) {

        if (schoolRepository.existsByName(schoolName)) {
            throw new BadRequestException
                    (String.format("A school with name \"%s\" already exists", schoolName));
        }

        SchoolEntity school = schoolRepository.saveAndFlush(
                SchoolEntity.makeDefault(schoolName));

        return ResponseEntity.ok(schoolDTOFactory.createSchoolDTO(school));
    }

    @GetMapping(FETCH_SCHOOL)
    public ResponseEntity<List<SchoolDTO>> fetchSchools(@RequestParam String filter) {

            boolean isFiltered = !filter.trim().isEmpty();

            List<SchoolEntity> schools = schoolRepository.findAllByFilter(isFiltered, filter);

            return ResponseEntity.ok(schoolDTOFactory.createSchoolDTOList(schools));
    }

    @DeleteMapping(DELETE_SCHOOL)
    public ResponseEntity<AckDTO> deleteSchool(@PathVariable Long schoolId) {

        if (schoolRepository.existsById(schoolId)) {
            throw new NotFoundException(String.format("A school with id \"%s\" does not exist", schoolId));
        }


    }
}
