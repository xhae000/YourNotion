package com.xhadl.yournotion.Validator;

import com.xhadl.yournotion.DTO.SurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SurveyValidator {

    @Autowired
    private CommonValidator commonValidator;

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
}
