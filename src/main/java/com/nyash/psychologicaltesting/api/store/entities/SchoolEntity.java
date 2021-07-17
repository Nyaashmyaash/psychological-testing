package com.nyash.psychologicaltesting.api.store.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "school")
public class SchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    List<SchoolClassEntity> schoolClasses = new ArrayList<>();

    public static SchoolEntity makeDefault(String schoolName) {
        return builder()
                .name(schoolName)
                .build();
    }
}
