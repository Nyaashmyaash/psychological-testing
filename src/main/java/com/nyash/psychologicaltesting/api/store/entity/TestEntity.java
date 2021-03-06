package com.nyash.psychologicaltesting.api.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 10485760)
    String name;

    @Builder.Default
    @Column(name = "is_started")
    Boolean isStarted = false;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    List<QuestionEntity> questions = new ArrayList<>();

    public static TestEntity makeDefault() {
        return builder().build();
    }
}
