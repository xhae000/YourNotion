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
        this.survey_id = survey_id;
        this.question_type = question_type;
        this.question = question;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String question_type;
    String question;
    String image;
    int survey_id;
}
