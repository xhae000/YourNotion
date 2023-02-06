package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveyAvailableEntity;
import com.xhadl.yournotion.Entity.SurveyParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyParticipantRepository extends JpaRepository<SurveyParticipantEntity, Integer> {
    public SurveyParticipantEntity save(SurveyAvailableEntity surveyAvailableEntity);
    public int countBySurveyId(int survey_id);
    public SurveyParticipantEntity findBySurveyIdAndParticipantId(int surveyId, int participantId);
    public List<SurveyParticipantEntity> findByParticipantId(int participantId);
}
