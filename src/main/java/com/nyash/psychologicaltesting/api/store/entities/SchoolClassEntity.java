package com.nyash.psychologicaltesting.api.store.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "school_class")
public class SchoolClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(name = "name")
    String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    SchoolEntity school;

    @Column(name = "school_id", updatable = false, insertable = false)
    Long schoolId;

    public static SchoolClassEntity makeDefault(String name, SchoolEntity school) {
        return builder()
                .name(name)
                .school(school)
                .build();
    }
}
