package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.Service.SurveyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SurveyResultController {
    @Autowired
    SurveyResultService surveyResultService;

    @GetMapping("/survey/result/{id}")
    public String surveyResult(@PathVariable("id")int surveyId, Model model){
        // 설문 제작자가 아닌 사람이 접근 시
        if(!surveyResultService.isMadeByUser(surveyId))
            return "redirect:/";


        return "";
    }

}
