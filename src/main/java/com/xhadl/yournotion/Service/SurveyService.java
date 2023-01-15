package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface SurveyService {
    public SurveyDTO findById(int id);
    List<SurveyDTO> getSurveyList(Pageable pageable);
    public Integer createSurvey(SurveyDTO survey, QuestionListDTO questions, List<String> options) throws IOException;
    public int getSurveyCount();
}
