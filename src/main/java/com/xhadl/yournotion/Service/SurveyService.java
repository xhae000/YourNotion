package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

public interface SurveyService {
    public SurveyDTO findById(int id);
    List<SurveyDTO> getSurveyList(Pageable pageable);
    public Integer createSurvey(SurveyDTO survey, QuestionListDTO questions, List<String> options) throws IOException;
    public int getSurveyCount();
    public void setSurveyDetail(Model model, int id);
    public boolean isParticipatedSurvey(int surveyId);
    public boolean addSurveyWant(int surveyId);
    public Integer getSurveySeeCount(int surveyId);
}
