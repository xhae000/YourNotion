package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.Service.SurveyFormService;
import com.xhadl.yournotion.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayDeque;
import java.util.List;

@Controller
public class SurveyFormController {

    @Autowired
    SurveyService surveyService;
    @Autowired
    SurveyFormService surveyFormService;

    @GetMapping("/survey/participate/{id}")
    public String surveyForm (@PathVariable("id") int surveyId, Model model){
        /* 설문에 참여 못하는 조건
         * 1. 만료된 설문
         * 2. 참여한 설문
         * 3. 설문 대상과 유저의 성별, 나이가 맞지 않는 설문
        */
        if(!surveyFormService.validSurvey(surveyId))
            return "redirect:/";

        model.addAttribute("questions", surveyFormService.getQuestions(surveyId));
        model.addAttribute("surveyTitle",surveyFormService.getSurveyTitle(surveyId));

        return "/survey/surveyForm";
    }

    @PostMapping("/survey/submit")
    public String surveySubmit(
            @RequestParam(value="surveyId") int surveyId,
            @RequestParam(value="answer") List<String> answers){
        surveyFormService.submitSurvey(surveyId,new ArrayDeque<>(answers));
        return "redirect:/";
    }
}
