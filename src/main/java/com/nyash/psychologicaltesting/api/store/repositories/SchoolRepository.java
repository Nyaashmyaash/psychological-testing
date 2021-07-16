package com.nyash.psychologicaltesting.api.store.repositories;

import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    Boolean existsByName(@NonNull String name);

    @Query("SELECT s FROM SchoolEntity s" +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<SchoolEntity> findAllByFilter(String filter);
}
