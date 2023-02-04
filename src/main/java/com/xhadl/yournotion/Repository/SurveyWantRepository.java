package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveyWantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyWantRepository extends JpaRepository<SurveyWantEntity, Integer> {
    public SurveyWantEntity findByUserIdAndSurveyId(int userId, int surveyId);
    public SurveyWantEntity save(SurveyWantEntity surveyWantEntity);
}
