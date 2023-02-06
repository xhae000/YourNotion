package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.MySurveyDTO;

import java.util.List;

public interface MySurveyService {

    public List<MySurveyDTO> getParticipatedSurveys();
    public List<MySurveyDTO> getMadeSurveys();
}
