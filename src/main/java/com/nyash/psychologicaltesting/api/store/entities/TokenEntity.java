package com.nyash.psychologicaltesting.api.store.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class TokenEntity {

    private static final int EXPIRED_SECONDS = 60 * 60; // One hour expired

    @Id
    @Builder.Default
    private String token = UUID.randomUUID().toString();

    @Builder.Default
    private Instant expiredAt = Instant.now().plusSeconds(EXPIRED_SECONDS);

    @Builder.Default
    private Instant createdAt = Instant.now();
}
