package com.xhadl.yournotion.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="survey_participant")
public class SurveyParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /* JPA에서 언더바가 들어간 칼럼명을 인식 못해서, @Calumn 어노테이션 사용 */
    @Column(name = "survey_id")
    int surveyId;
    @Column(name = "participant_id")
    int participantId;

}
