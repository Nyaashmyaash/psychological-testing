package com.nyash.psychologicaltesting.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDTO {

    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    Boolean isStarted;

}
