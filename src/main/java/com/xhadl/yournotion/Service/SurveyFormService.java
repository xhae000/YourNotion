package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.QuestionFormDTO;

import java.util.List;

public interface SurveyFormService {

    public Boolean validSurvey(int surveyId);
    public List<QuestionFormDTO> getQuestions(int surveyId);
    public String getSurveyTitle(int surveyId);

}
