package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.exceptions.BadRequestException;
import com.nyash.psychologicaltesting.api.factory.SchoolDTOFactory;
import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import com.nyash.psychologicaltesting.api.store.repositories.UserRepository;
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
public class UserController {

    UserRepository userRepository;

    SchoolDTOFactory schoolDTOFactory;

    public static final String FETCH_USERS = "/api/users";
    public static final String CREATE_USER = "/api/users/{userName}";
    public static final String DELETE_USER = "/api/users/{userId}";


    @PostMapping(CREATE_USER)
    public ResponseEntity<SchoolDTO> createUser(@PathVariable String schoolName) {

        if (schoolRepository.existsByName(schoolName)) {
            throw new BadRequestException
                    (String.format("A school with name \"%s\" already exists", schoolName));
        }

        SchoolEntity school = schoolRepository.saveAndFlush(
                SchoolEntity.makeDefault(schoolName));

        return ResponseEntity.ok(schoolDTOFactory.createSchoolDTO(school));
    }

    @GetMapping(FETCH_USERS)
    public ResponseEntity<List<SchoolDTO>> fetchUsers(@RequestParam String filter) {

            boolean isFiltered = !filter.trim().isEmpty();

            List<SchoolEntity> schools = schoolRepository.findAllByFilter(isFiltered, filter);

            return ResponseEntity.ok(schoolDTOFactory.createSchoolDTOList(schools));
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity<AckDTO> deleteUser(@PathVariable Long userId) {

        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }

        return ResponseEntity.ok(AckDTO.makeDefault(true));

    }
}
