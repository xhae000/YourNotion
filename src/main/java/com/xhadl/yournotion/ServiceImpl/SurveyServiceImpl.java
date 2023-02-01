package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.QuestionDTO;
import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.OptionEntity;
import com.xhadl.yournotion.Entity.QuestionEntity;
import com.xhadl.yournotion.Entity.SurveyEntity;
import com.xhadl.yournotion.Formatter.SurveyListFormatter;
import com.xhadl.yournotion.Repository.*;
import com.xhadl.yournotion.Service.SurveyService;
import com.xhadl.yournotion.Validator.OptionValidator;
import com.xhadl.yournotion.Validator.QuestionValidator;
import com.xhadl.yournotion.Validator.SurveyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private SurveyParticipantRepository surveyParticipantRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionValidator questionValidator;
    @Autowired
    private OptionValidator optionValidator;
    @Autowired
    private SurveyValidator surveyValidator;

    @Autowired
    SurveyListFormatter timeFormatter;


    @Override
    public SurveyDTO findById(int id){
        SurveyEntity surveyEntity = surveyRepository.findById(id);
        return modelMapper.map(surveyEntity, SurveyDTO.class);
    }


    @Override
    public List<SurveyDTO> getSurveyList(Pageable pageable){
        List<SurveyEntity> surveyEntities = surveyRepository.findAllByOrderByIdDesc(pageable).getContent();
        List<SurveyDTO> surveys = surveyEntities.stream()
                .map(p -> modelMapper.map(p, SurveyDTO.class)).collect(Collectors.toList());

        // 정보 포맷
        for(SurveyDTO survey : surveys) {
            int count = surveyParticipantRepository.countBySurveyId(survey.getId());
            survey.formatForSurveyList(timeFormatter, count);
        }
        return surveys;
    }

/* 주관식 객관식 처리필요*/
    @Override
    public Integer createSurvey(SurveyDTO survey, QuestionListDTO questions, List<String> options) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;

        String username = userDetails.getUsername();
        int userId = userRepository.findByUsername(username).get().getId();

        // QuestionDTO 검증과 동시에 File 받아오기 ( 문제가 발생한다면 null을 반환)
        ArrayList<String> file_result = questionValidator.validate(questions);

        if(!surveyValidator.validate(survey) || !optionValidator.validate(options) || file_result == null)
            return null;

        survey.setMaker_id(userId); // 설문 작성자 설정

        SurveyEntity surveyEntity = modelMapper.map(survey, SurveyEntity.class);
        int survey_id = surveyRepository.save(surveyEntity).getId(); // 설문 insert

        List<QuestionDTO> questionList = questions.getQuestion();
        List<Integer> question_id = new ArrayList<>();

        for(int i=0;i<questions.getQuestion().size();i++){
            QuestionEntity questionEntity =
                    new QuestionEntity(
                            survey_id,
                            questionList.get(i).getQuestion_type(),
                            questionList.get(i).getQuestion(),
                            file_result.get(i)
                    );
            question_id.add(questionRepository.save(questionEntity).getId());
        }

        // options는 [key, value, key, value,...] 형식의 list 임
        int key = 0;

        for(int j=0;j<options.size();j++){
            if(j%2 == 0)
                key = question_id.get(Integer.parseInt(options.get(j)));

            else{
                OptionEntity optionEntity =
                        new OptionEntity(options.get(j),key);

                optionRepository.save(optionEntity);
            }

        }

        return survey_id;
    }

    @Override
    public int getSurveyCount(){
        return surveyRepository.countBy();
    }

    @Override
    public void setSurveyDetail(Model model, int id){
        SurveyDTO survey = findById(id);
        survey.formatForSurveyList(timeFormatter, surveyParticipantRepository.countBySurveyId(survey.getId()));

        model.addAttribute("isInSession", timeFormatter.formatSession(survey.getTime()).get("isInSession"));
        model.addAttribute("isAvailable", surveyValidator.isAvailable(survey).toString());
        model.addAttribute("survey",survey);
        model.addAttribute("maker_nickname",userRepository.findById(survey.getMaker_id()).getNickname());
        model.addAttribute("question_cnt", questionRepository.countBySurveyId(id));
    }
}
