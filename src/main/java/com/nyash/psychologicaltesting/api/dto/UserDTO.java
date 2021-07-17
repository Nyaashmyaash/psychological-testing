package com.nyash.psychologicaltesting.api.dto;

import com.nyash.psychologicaltesting.api.domains.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @NonNull
    Long id;

    @NonNull
    String firstName;

    String middleName;

    @NonNull
    String lastName;

    @NonNull
    String login;

    @NonNull
    String password;

    @NonNull
    Instant birthday;

    @NonNull
    UserRole role;

    @NonNull
    Long schoolClassId;
}
