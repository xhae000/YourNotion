package com.xhadl.yournotion.Validator;

import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Formatter.SurveyListFormatter;
import com.xhadl.yournotion.Formatter.UserFormatter;
import com.xhadl.yournotion.Repository.SurveyParticipantRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SurveyValidator {

    @Autowired
    private CommonValidator commonValidator;
    @Autowired
    private SurveyListFormatter timeFormatter;
    @Autowired
    private UserFormatter userFormatter;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurveyParticipantRepository surveyParticipantRepository;

    public Boolean validate(SurveyDTO survey){
        if(!commonValidator.validate_size(survey.getTitle(),1,32))
            return false;

        if (!commonValidator.validate_size(survey.getDes(),1,500))
            return false;

        if(survey.getStartAge()>survey.getEndAge())
            return false;


        if (survey.getStartAge()<1 || survey.getEndAge()>99)
            return false;

        String gender = survey.getGender();

        if (gender.equals("male") || gender.equals("female") || gender.equals("irr"))
            return true;

        return false;
    }

    public Boolean isAvailable(SurveyDTO survey) {
        /* 1. 날짜 만료 검사 */
        if (timeFormatter.formatSession(survey.getTime()).get("isInSession").equals("false"))
            return false;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        Optional<UserEntity> user = userRepository.findByUsername(((UserDetails) principal).getUsername());
        if (user.isEmpty()) return false;

        /* 2. 이미 참여한 설문인지 검사  */
        if (surveyParticipantRepository.findBySurveyIdAndParticipantId(survey.getId(),user.get().getId())!=null)
            return false;

        /* 3. 성별 검사 */
        if (!user.get().getGender().equals(survey.getGender()) && !survey.getGender().equals("irr"))
            return false;

        /* 4. 나이 검사 */
        int userAge = userFormatter.formatAge(user.get().getAge());
        return survey.getStartAge() <= userAge && survey.getEndAge() >= userAge;
    }
}
