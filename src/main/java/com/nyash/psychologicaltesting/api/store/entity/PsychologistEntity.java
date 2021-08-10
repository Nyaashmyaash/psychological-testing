package com.nyash.psychologicaltesting.api.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "psychologist")
public class PsychologistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(name = "first_name")
    String firstName;

    @NonNull
    @Column(name = "last_name")
    String lastName;

    @NonNull
    @Column(name = "login")
    String login;

    @NonNull
    @Column(name = "password")
    String password;
}
