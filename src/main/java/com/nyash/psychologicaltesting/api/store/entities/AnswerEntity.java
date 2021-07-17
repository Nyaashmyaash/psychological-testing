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
@Table(name = "answer")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_order")
    private Integer answerOrder;

    @Column(name = "name", length = 10485760)
    private String name;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private QuestionEntity question;

    @Column(name = "question_id", updatable = false, insertable = false)
    private Long questionId;

    public static AnswerEntity makeDefault() {
        return builder().build();
    }
}
