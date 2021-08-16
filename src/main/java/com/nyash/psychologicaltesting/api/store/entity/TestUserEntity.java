package com.nyash.psychologicaltesting.api.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "test_user")
public class TestUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @ManyToOne
    UserEntity user;

    @NonNull
    @ManyToOne
    TestEntity test;

    @Column(length = 10485760)
    @NonNull
    String answers;

    @Builder.Default
    @NonNull
    @Column(name = "create_at")
    Instant createdAt = Instant.now();

    @Column(name = "psychologist_id", insertable = false, updatable = false)
    Long psychologistId;
}
