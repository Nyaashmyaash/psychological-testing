package com.nyash.psychologicaltesting.api.store.repository;

import com.nyash.psychologicaltesting.api.store.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
