package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.QuestionDTO;
import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.OptionEntity;
import com.xhadl.yournotion.Entity.QuestionEntity;
import com.xhadl.yournotion.Entity.SurveyEntity;
import com.xhadl.yournotion.Repository.OptionRepository;
import com.xhadl.yournotion.Repository.QuestionRepository;
import com.xhadl.yournotion.Repository.SurveyRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import com.xhadl.yournotion.Service.SurveyService;
import com.xhadl.yournotion.Validator.OptionValidator;
import com.xhadl.yournotion.Validator.QuestionValidator;
import com.xhadl.yournotion.Validator.SurveyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionValidator questionValidator;
    @Autowired
    private OptionValidator optionValidator;
    @Autowired
    private SurveyValidator surveyValidator;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;



    @Override
    public SurveyDTO findById(int id){
        SurveyEntity surveyEntity = surveyRepository.findById(id);
        System.out.println(surveyEntity.getTitle());

        SurveyDTO surveyDTO = modelMapper.map(surveyEntity, SurveyDTO.class);
        System.out.println(surveyDTO.toString());

        return surveyDTO;
    }


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

}
