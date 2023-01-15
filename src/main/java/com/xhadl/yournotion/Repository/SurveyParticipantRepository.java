package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveyParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyParticipantRepository extends JpaRepository<SurveyParticipantEntity, Long> {
    public int countBySurveyId(int survey_id);
}
