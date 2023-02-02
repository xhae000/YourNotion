package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.QuestionFormDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.AnswerEntity;
import com.xhadl.yournotion.Entity.QuestionEntity;
import com.xhadl.yournotion.Entity.SurveyParticipantEntity;
import com.xhadl.yournotion.Repository.*;
import com.xhadl.yournotion.Service.SurveyFormService;
import com.xhadl.yournotion.Validator.SurveyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
public class SurveyFormServiceImpl implements SurveyFormService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SurveyValidator surveyValidator;
    @Autowired
    private SurveyParticipantRepository surveyParticipantRepository;
    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public String getSurveyTitle(int surveyId){
        return surveyRepository.findById(surveyId).getTitle();
    }

    @Override
    public void submitSurvey(int surveyId, Deque<String> answers){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();

        for(int questionId : questionRepository.getQuestionsId(surveyId))
            answerRepository.save(new AnswerEntity(questionId, answers.pollFirst()));
        surveyParticipantRepository.save(new SurveyParticipantEntity(surveyId, userRepository.getUserId(username)));
    }
}
