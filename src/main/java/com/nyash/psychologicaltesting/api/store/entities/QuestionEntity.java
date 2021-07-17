package com.nyash.psychologicaltesting.api.store.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_order")
    private Integer questionOrder;

    @Column(name = "text", length = 10485760)
    private String text;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private TestEntity test;

    @Column(name = "test_id", updatable = false, insertable = false)
    private Long testId;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private List<AnswerEntity> answers = new ArrayList<>();

    public static QuestionEntity makeDefault() {
        return builder().build();
    }
}
