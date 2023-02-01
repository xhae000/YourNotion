package com.xhadl.yournotion.Validator;

import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Formatter.SurveyListFormatter;
import com.xhadl.yournotion.Formatter.UserFormatter;
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

    public Boolean validate(SurveyDTO survey){
        if(!commonValidator.validate_size(survey.getTitle(),1,32))
            return false;

        if (!commonValidator.validate_size(survey.getDes(),1,500))
            return false;

        if(survey.getStart_age()>survey.getEnd_age())
            return false;


        if (survey.getStart_age()<1 || survey.getEnd_age()>99)
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

        /* 2. 성별 검사 */
        if (!user.get().getGender().equals(survey.getGender()) && !survey.getGender().equals("irr"))
            return false;

        /* 3. 나이 검사 */
        int userAge = userFormatter.formatAge(user.get().getAge());
        return survey.getStart_age() <= userAge && survey.getEnd_age() >= userAge;
    }
}
