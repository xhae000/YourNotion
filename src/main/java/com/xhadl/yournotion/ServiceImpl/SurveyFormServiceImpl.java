package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.QuestionFormDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.QuestionEntity;
import com.xhadl.yournotion.Repository.OptionRepository;
import com.xhadl.yournotion.Repository.QuestionRepository;
import com.xhadl.yournotion.Repository.SurveyRepository;
import com.xhadl.yournotion.Service.SurveyFormService;
import com.xhadl.yournotion.Validator.SurveyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyFormServiceImpl implements SurveyFormService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyValidator surveyValidator;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Boolean validSurvey(int surveyId) {
        // 나이 성별 검사로직 추가
        return surveyValidator.isAvailable(modelMapper.map(surveyRepository.findById(surveyId), SurveyDTO.class));
    }

    @Override
    public List<QuestionFormDTO> getQuestions(int surveyId)     {
        List<QuestionFormDTO> questionForms = new ArrayList<>();
        List<QuestionEntity> questions = questionRepository.findAllBySurveyId(surveyId);

        for ( QuestionEntity q : questions)
            questionForms.add(new QuestionFormDTO(q, optionRepository.findAllByQuestionId(q.getId())));

        return questionForms;
    }

    public String getSurveyTitle(int surveyId){
        return surveyRepository.findById(surveyId).getTitle();
    }

}
