package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.Service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    SurveyService surveyService;

    @RequestMapping("/")
    public String index(){

        return "/index";
    }
}
