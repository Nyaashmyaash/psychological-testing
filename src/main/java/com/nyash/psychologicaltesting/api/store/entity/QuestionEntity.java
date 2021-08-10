package com.nyash.psychologicaltesting.api.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "question_order")
    Integer questionOrder;

    @Column(name = "text", length = 10485760)
    String text;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    TestEntity test;

    @Column(name = "test_id", updatable = false, insertable = false)
    Long testId;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    List<AnswerEntity> answers = new ArrayList<>();

    public static QuestionEntity makeDefault() {
        return builder().build();
    }
}
