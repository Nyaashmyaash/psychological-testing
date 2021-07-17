package com.nyash.psychologicaltesting.api.store.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_user")
public class TestUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private UserEntity user;

    @NonNull
    @ManyToOne
    private TestEntity test;

    @Column(length = 10485760)
    @NonNull
    private String answers;

    @Builder.Default
    @NonNull
    @Column(name = "create_at")
    private Instant createdAt = Instant.now();

    @Column(name = "psychologist_id", insertable = false, updatable = false)
    private Long psychologistId;
}
