package com.xhadl.yournotion.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class QuestionEntity {

    public QuestionEntity(int survey_id, String question_type, String question, String image){
        this.surveyId = survey_id;
        this.questionType = question_type;
        this.question = question;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="question_type")
    String questionType;
    String question;
    String image;
    @Column(name = "survey_id")
    int surveyId;
}
