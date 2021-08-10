package com.nyash.psychologicaltesting.api.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nyash.psychologicaltesting.api.domain.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(name = "first_name")
    String firstName;

    @NonNull
    @Column(name = "last_name")
    String lastName;

    @Column(name = "middle_name")
    String middleName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NonNull
    @Column(name = "birthday")
    Instant birthday;

    @Column(name = "login")
    String login;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    UserRole role;

    @NonNull
    @ManyToOne
    SchoolClassEntity schoolClass;

    public static UserEntity makeDefault(
            String firstName,
            String middleName,
            String lastName,
            String login,
            String password,
            Instant birthday,
            UserRole role,
            SchoolClassEntity schoolClass) {
        return builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .login(login)
                .password(password)
                .birthday(birthday)
                .role(role)
                .schoolClass(schoolClass)
                .build();
    }
}
