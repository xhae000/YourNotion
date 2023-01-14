package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
    public SurveyEntity findById(int id);
    public SurveyEntity save(SurveyEntity surveyEntity);
    public int countBy();
}
