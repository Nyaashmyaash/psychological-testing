package com.nyash.psychologicaltesting.api.store.repository;

import com.nyash.psychologicaltesting.api.store.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT u FROM UserEntity u WHERE u.id =:userId AND u.schoolClass.id =:schoolClassId")
    Optional<UserEntity> findByIdAndSchoolClassId(Long userId, Long schoolClassId);

    Optional<UserEntity> findTopByLoginAndPassword(@NonNull String login, @NonNull String password);
}
