package com.nyash.psychologicaltesting.api.store.repositories;

import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u " +
            "WHERE :isFiltered = FALSE " +
            "OR (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :filter, '%')))" +
            "ORDER BY u.lastName, u.firstName")
    List<UserEntity> findAllByFilter(boolean isFiltered, String filter);


    @Query("SELECT u FROM UserEntity u " +
            "WHERE u.schoolClass.id =:classId " +
            "AND (:isFiltered = FALSE " +
            "OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
            ") " +
            "ORDER BY u.lastName, u.firstName")
    List<UserEntity> findAllByFilterAndClass(boolean isFiltered, String filter, Long classId);
}
