package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.Service.SurveyFormService;
import com.xhadl.yournotion.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SurveyFormController {

    @Autowired
    SurveyService surveyService;
    @Autowired
    SurveyFormService surveyFormService;

    @GetMapping("/survey/participate/{id}")
    public String surveyForm (@PathVariable("id") int surveyId, Model model){
        if(!surveyFormService.validSurvey(surveyId)) // 만료된 설문
            return "redirect:/";

        model.addAttribute("questions", surveyFormService.getQuestions(surveyId));
        model.addAttribute("surveyTitle",surveyFormService.getSurveyTitle(surveyId));

        return "/survey/surveyForm";
    }
}
