package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class SurveyController {

    @Autowired
    SurveyService surveyService;


    @GetMapping("/survey/create")
    public String createSurvey(){
        return "/survey/create";
    }

    @PostMapping("/survey/createProc")
    public String createSurveyProc(
             SurveyDTO survey,
             QuestionListDTO question,
             @RequestParam(value="options") List<String> options
    ) throws IOException {
        System.out.println(surveyService.createSurvey(survey, question, options));
        return "redirect:/";
    }

    @GetMapping("/surveyList")
    public String surveyList(){
        return "/survey/surveyList";
    }
}
