package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.exception.BadRequestException;
import com.nyash.psychologicaltesting.api.factory.SchoolDTOFactory;
import com.nyash.psychologicaltesting.api.service.ControllerAuthenticationService;
import com.nyash.psychologicaltesting.api.store.entity.SchoolEntity;
import com.nyash.psychologicaltesting.api.store.repository.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class SchoolController {

    SchoolRepository schoolRepository;

    SchoolDTOFactory schoolDTOFactory;

    ControllerAuthenticationService authenticationService;

    public static final String FETCH_SCHOOLS = "/api/schools";
    public static final String CREATE_SCHOOL = "/api/schools/{schoolName}";
    public static final String DELETE_SCHOOL = "/api/schools/{schoolId}";


    @PostMapping(CREATE_SCHOOL)
    public ResponseEntity<SchoolDTO> createSchool(
            @PathVariable String schoolName,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        if (schoolRepository.existsByName(schoolName)) {
            throw new BadRequestException
                    (String.format("Школа с названием \"%s\" уже существует", schoolName));
        }

        SchoolEntity school = schoolRepository.saveAndFlush(
                SchoolEntity.makeDefault(schoolName));

        return ResponseEntity.ok(schoolDTOFactory.createSchoolDTO(school));
    }

    @GetMapping(FETCH_SCHOOLS)
    public ResponseEntity<List<SchoolDTO>> fetchSchools(@RequestParam String filter) {

            boolean isFiltered = !filter.trim().isEmpty();

            List<SchoolEntity> schools = schoolRepository.findAllByFilter(isFiltered, filter);

            return ResponseEntity.ok(schoolDTOFactory.createSchoolDTOList(schools));
    }

    @DeleteMapping(DELETE_SCHOOL)
    public ResponseEntity<AckDTO> deleteSchool(
            @PathVariable Long schoolId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        if (schoolRepository.existsById(schoolId)) {
            schoolRepository.deleteById(schoolId);
        }

        return ResponseEntity.ok(AckDTO.makeDefault(true));

    }
}
