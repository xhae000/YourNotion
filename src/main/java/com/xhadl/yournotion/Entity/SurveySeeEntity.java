package com.xhadl.yournotion.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name="survey_see")
public class SurveySeeEntity {

    public SurveySeeEntity(int surveyId, int seeCount){
        this.surveyId = surveyId;
        this.seeCount = seeCount;
    }

    @Id
    int id;
    @Column(name = "survey_id")
    int surveyId;
    @Column(name = "see_count")
    int seeCount;
}
