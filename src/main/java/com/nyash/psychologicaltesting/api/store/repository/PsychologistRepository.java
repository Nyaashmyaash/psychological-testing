package com.nyash.psychologicaltesting.api.store.repository;

import com.nyash.psychologicaltesting.api.store.entity.PsychologistEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsychologistRepository extends JpaRepository<PsychologistEntity, Long> {
    Optional<PsychologistEntity> findTopByLoginAndPassword(@NonNull String login, @NonNull String password);
}
