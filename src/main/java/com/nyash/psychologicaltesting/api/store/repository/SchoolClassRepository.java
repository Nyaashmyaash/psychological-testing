package com.nyash.psychologicaltesting.api.store.repository;

import com.nyash.psychologicaltesting.api.store.entity.SchoolClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, Long> {
    void deleteByIdAndSchoolId(Long schoolClassId, Long schoolId);
}
