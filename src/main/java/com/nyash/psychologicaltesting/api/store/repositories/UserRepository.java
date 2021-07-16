package com.nyash.psychologicaltesting.api.store.repositories;

import com.nyash.psychologicaltesting.api.store.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
