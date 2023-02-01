package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Entity.SurveyAvailableEntity;
import com.xhadl.yournotion.Service.SurveyService;
import com.xhadl.yournotion.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private UserService userService;

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

        // 생성과 동시에 survey id 반환
        int survey_id = surveyService.createSurvey(survey, question, options);
        return "redirect:/survey/"+survey_id;
    }

    @GetMapping("/surveyList")
    public String surveyList(@PageableDefault(size=10) Pageable pageable, Model model, Authentication auth){
        SurveyAvailableEntity userInfo = userService.addAgeGenderModel(auth);

        model.addAttribute("user_age", userInfo.getFormatAge());
        model.addAttribute("user_gender", userInfo.getGender());
        model.addAttribute("surveys", surveyService.getSurveyList(pageable));

        return "/survey/surveyList";
    }

    @GetMapping("/survey/{id}")
    public String surveyDetail(@PathVariable int id, Model model, Authentication auth){
        surveyService.setSurveyDetail(model , id);
        return "/survey/detail";
    }
}
