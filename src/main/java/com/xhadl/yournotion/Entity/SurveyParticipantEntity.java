package com.xhadl.yournotion.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="survey_participant")
public class SurveyParticipantEntity {
    public SurveyParticipantEntity(int surveyId, int participantId){
        this.surveyId = surveyId;
        this.participantId = participantId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /* JPA에서 언더바가 들어간 칼럼명을 인식 못해서, @Calumn 어노테이션 사용 */
    @Column(name = "survey_id")
    int surveyId;
    @Column(name = "participant_id")
    int participantId;
    String time;

}
