package com.nyash.psychologicaltesting.api.store.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "school_class")
public class SchoolClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private SchoolEntity school;

    @Column(name = "school_id", updatable = false, insertable = false)
    private Long schoolId;

    public static SchoolClassEntity makeDefault(String name, SchoolEntity school) {
        return builder()
                .name(name)
                .school(school)
                .build();
    }
}
