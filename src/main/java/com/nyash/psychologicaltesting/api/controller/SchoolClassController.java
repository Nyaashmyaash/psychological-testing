package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.SchoolClassDTO;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolClassRepository;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class SchoolClassController {

    SchoolClassRepository schoolClassRepository;

    SchoolRepository schoolRepository;

    private static final String FETCH_SCHOOL_CLASSES = "/api/schools/classes";
    private static final String CREATE_SCHOOL_CLASS = "/api/schools/{schoolId}/classes/{className}";

    @GetMapping(FETCH_SCHOOL_CLASSES)
    public ResponseEntity<List<SchoolClassDTO>> fetchSchoolClasses() {

        return ResponseEntity.ok(null);
    }

    @PostMapping(CREATE_SCHOOL_CLASS)
    public ResponseEntity<SchoolClassDTO> createSchoolClass(
            @PathVariable String className,
            @PathVariable Long schoolId) {

        SchoolEntity school = schoolRepository
                .findById(schoolId)
                .orElseThrow(()-> new NotFoundException
                        (String.format("A school with id \"%s\", cannot found", schoolId)));
    }
}
