package com.xhadl.yournotion.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "survey_want")
public class SurveyWantEntity {
    public SurveyWantEntity(int userId, int surveyId){
        this.userId = userId;
        this.surveyId = surveyId;
    }

    @Id
    int id;
    @Column(name="user_id")
    int userId;
    @Column(name="survey_id")
    int surveyId;
}
