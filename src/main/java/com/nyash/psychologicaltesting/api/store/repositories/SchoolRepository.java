package com.nyash.psychologicaltesting.api.store.repositories;

import com.nyash.psychologicaltesting.api.store.entities.SchoolEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    Boolean existsByName(@NonNull String name);
}
