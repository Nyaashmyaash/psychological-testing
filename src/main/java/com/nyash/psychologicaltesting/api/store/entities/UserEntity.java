package com.nyash.psychologicaltesting.api.store.entities;

import com.nyash.psychologicaltesting.api.domains.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middlename;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NonNull
    @ManyToOne
    private SchoolClassEntity schoolClass;

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
                .middlename(middleName)
                .lastName(lastName)
                .login(login)
                .password(password)
                .birthday(birthday)
                .role(role)
                .schoolClass(schoolClass)
                .build();
    }
}
