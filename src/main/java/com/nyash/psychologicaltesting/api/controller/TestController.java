package com.nyash.psychologicaltesting.api.controller;

import com.nyash.psychologicaltesting.api.domains.UserRole;
import com.nyash.psychologicaltesting.api.dto.AckDTO;
import com.nyash.psychologicaltesting.api.dto.SchoolDTO;
import com.nyash.psychologicaltesting.api.dto.UserDTO;
import com.nyash.psychologicaltesting.api.exceptions.NotFoundException;
import com.nyash.psychologicaltesting.api.factory.UserDTOFactory;
import com.nyash.psychologicaltesting.api.store.entities.SchoolClassEntity;
import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import com.nyash.psychologicaltesting.api.store.repositories.SchoolClassRepository;
import com.nyash.psychologicaltesting.api.store.repositories.TestRepository;
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
public class TestController {

    TestRepository testRepository;

    public static final String FETCH_TESTS = "/api/tests";
    public static final String CREATE_OR_UPDATE_TEST = "/api/tests";
    public static final String DELETE_TEST = "/api/tests/{testId}";




}
