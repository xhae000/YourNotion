package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.Service.MySurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MySurveyController {

    @Autowired
    MySurveyService mySurveyService;

    @GetMapping("/survey/wantedSurvey")
    public String wantedSurvey(){

        return "/mySurvey/wantedSurvey";
    }

    @GetMapping("/survey/participatedSurvey")
    public String participatedSurvey(){
        return "/mySurvey/participatedSurvey";
    }

    @GetMapping("/survey/madeSurvey")
    public String madeSurvey(Model model){
        model.addAttribute("surveys", mySurveyService.getMadeSurveys());
        return "/mySurvey/madeSurvey";
    }
}
