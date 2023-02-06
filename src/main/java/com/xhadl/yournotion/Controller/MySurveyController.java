package com.xhadl.yournotion.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MySurveyController {
    @GetMapping("/survey/wantedSurvey")
    public String wantedSurvey(){
        return "/mySurvey/wantedSurvey";
    }

    @GetMapping("/survey/participatedSurvey")
    public String participatedSurvey(){
        return "/mySurvey/participatedSurvey";
    }

    @GetMapping("/survey/madeSurvey")
    public String madeSurvey(){
        return "/mySurvey/madeSurvey";
    }
}
