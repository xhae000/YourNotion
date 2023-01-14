package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;

import java.io.IOException;
import java.util.List;

public interface SurveyService {
    public SurveyDTO findById(int id);
    public Integer createSurvey(SurveyDTO survey, QuestionListDTO questions, List<String> options) throws IOException;
    public int getSurveyCount();
}
