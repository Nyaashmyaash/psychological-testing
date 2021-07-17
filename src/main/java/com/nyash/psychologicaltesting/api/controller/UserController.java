package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.domains.UserRole;
import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.exceptions.BadRequestException;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.SchoolDTOFactory;
import com.nyash.psychologicaltesting.api.store.entities.SchoolClassEntity;
import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolClassRepository;
import com.nyash.psychologicaltesting.api.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class UserController {

    UserRepository userRepository;

    SchoolClassRepository schoolClassRepository;

    SchoolDTOFactory schoolDTOFactory;

    public static final String FETCH_USERS = "/api/schools/classes/users";
    public static final String CREATE_USER = "/api/schools/classes/{classId}/users";
    public static final String DELETE_USER = "/api/schools/classes/users/{userId}";


    @PostMapping(CREATE_USER)
    public ResponseEntity<SchoolDTO> createUser(
            @RequestParam Instant birthday,
            @RequestParam String firstName,
            @RequestParam(defaultValue = "") String middleName,
            @RequestParam String lastName,
            @RequestParam UserRole userRole,
            @PathVariable Long classId) {

        firstName = firstName.trim();
        lastName = lastName.trim();
        middleName = middleName.trim().isEmpty() ? null : middleName;

        String login = makeLogin(firstName,lastName);
        String password = makePassword();

        SchoolClassEntity schoolClass = schoolClassRepository
                .findById(classId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Class with id \"%s\" cannot found", classId)));

        UserEntity user = userRepository.saveAndFlush(
                UserEntity.makeDefault(
                        firstName,
                        middleName,
                        lastName,
                        login,
                        password,
                        birthday,
                        userRole,
                        schoolClass
                )
        );

        return ResponseEntity.ok(userDTOFactory.createUserDTO(user));
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
