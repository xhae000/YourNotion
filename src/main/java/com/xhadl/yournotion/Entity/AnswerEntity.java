package com.xhadl.yournotion.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="answer")
public class AnswerEntity {
    public AnswerEntity(int questionId, String answer){
        this.questionId = questionId;
        this.answer = answer;
    }
    @Id
    int id;
    @Column(name="question_id")
    int questionId;
    String answer;
}
