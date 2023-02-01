package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository  extends JpaRepository<QuestionEntity, Long> {

    public QuestionEntity save(QuestionEntity questionEntity);

    public List<QuestionEntity> findAllBySurveyId(int surveyId);

    public int countBySurveyId(int id);
}
