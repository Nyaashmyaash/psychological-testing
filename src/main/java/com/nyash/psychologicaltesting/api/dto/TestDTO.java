package com.nyash.psychologicaltesting.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDTO extends LiteTestDTO{
    List<QuestionDTO> questions;
}
