package com.nyash.psychologicaltesting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {

    @JsonProperty("school_id")
    @NonNull
    private Long id;

    @NonNull
    private String name;


}
