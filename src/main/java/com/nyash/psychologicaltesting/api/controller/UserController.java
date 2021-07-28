package com.nyash.psychologicaltesting.api.controller;

import com.ibm.icu.text.Transliterator;
import com.nyash.psychologicaltesting.api.domains.UserRole;
import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.UserDTO;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.UserDTOFactory;
import com.nyash.psychologicaltesting.api.service.ControllerAuthenticationService;
import com.nyash.psychologicaltesting.api.store.entities.SchoolClassEntity;
import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolClassRepository;
import com.nyash.psychologicaltesting.api.store.repositories.UserRepository;
import com.nyash.psychologicaltesting.api.utils.StringChecker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@ExtensionMethod(StringChecker.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class UserController {

    UserDTOFactory userDTOFactory;

    UserRepository userRepository;

    SchoolClassRepository schoolClassRepository;

    ControllerAuthenticationService authenticationService;

    public static final String FETCH_USERS = "/api/schools/classes/users";
    public static final String FETCH_USERS_BY_CLASS = "/api/schools/classes/{classId}/users";
    public static final String CREATE_USER = "/api/schools/classes/{classId}/users";
    public static final String DELETE_USER = "/api/schools/classes/users/{userId}";
    public static final String GET_USER_ID_BY_LOGIN_AND_PASSWORD = "/api/schools/classes/users/id";

    private static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    private static final Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);

    private static final int PASSWORD_LENGTH = 10;


    @PostMapping(CREATE_USER)
    public ResponseEntity<UserDTO> createUser(
//            @RequestParam Instant birthday,
            @RequestParam String firstName,
            @RequestParam(defaultValue = "") String middleName,
            @RequestParam String lastName,
//            @RequestParam UserRole userRole,
            @PathVariable Long classId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        firstName = firstName.trim();
        lastName = lastName.trim();

        firstName.checkOnEmpty("firstName");
        lastName.checkOnEmpty("lastName");

        middleName = middleName.trim().isEmpty() ? null : middleName;

        String login = makeLogin(firstName, lastName);
        String password = makePassword();

        SchoolClassEntity schoolClass = schoolClassRepository
                .findById(classId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Класс с идентификатором \"%s\" не найден", classId)));

//        UserEntity user = userRepository.saveAndFlush(
//                UserEntity.makeDefault(
//                        firstName,
//                        middleName,
//                        lastName,
//                        login,
//                        password,
//                        birthday,
//                        userRole,
//                        schoolClass
//                )
//        );
        UserEntity user = userRepository.saveAndFlush(
                UserEntity.makeDefault(
                        firstName,
                        middleName,
                        lastName,
                        login,
                        password,
                        Instant.now(),
                        UserRole.STUDENT,
                        schoolClass
                )
        );

        return ResponseEntity.ok(userDTOFactory.createUserDTO(user));
    }

    @GetMapping(FETCH_USERS)
    public ResponseEntity<List<UserDTO>> fetchUsers(
            @RequestParam(defaultValue = "") String filter,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        boolean isFiltered = !filter.trim().isEmpty();

        List<UserEntity> users = userRepository.findAllByFilter(isFiltered, filter);

        return ResponseEntity.ok(userDTOFactory.createUserDTOList(users));
    }

    @GetMapping(FETCH_USERS_BY_CLASS)
    public ResponseEntity<List<UserDTO>> fetchUsersByClass (
            @RequestParam(defaultValue = "") String filter,
            @PathVariable Long classId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        boolean isFiltered = !filter.trim().isEmpty();

        List<UserEntity> users = userRepository.findAllByFilterAndClass(isFiltered, filter, classId);

        return ResponseEntity.ok(userDTOFactory.createUserDTOList(users));
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity<AckDTO> deleteUser(
            @PathVariable Long userId,
            @RequestHeader(defaultValue = "") String token) {

        authenticationService.authenticate(token);

        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }

        return ResponseEntity.ok(AckDTO.makeDefault(true));

    }

    @GetMapping(GET_USER_ID_BY_LOGIN_AND_PASSWORD)
    public ResponseEntity<Long> getUserIdByLoginAndPassword(
            @RequestParam String login,
            @RequestParam String password) {

        UserEntity user = userRepository
                .findTopByLoginAndPassword(login, password)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким логином и паролем не существует."));

        return ResponseEntity.ok(user.getId());
    }


    private String makeLogin(String firstName, String lastName){

        String firstNameTransliterated = toLatinTrans.transliterate(firstName.toLowerCase());

        String lastNameTransliterated = toLatinTrans.transliterate(lastName.toLowerCase());

        return String.format("%s.%s", firstNameTransliterated.charAt(0), lastNameTransliterated);
    }

    private String makePassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, PASSWORD_LENGTH);
    }
}


