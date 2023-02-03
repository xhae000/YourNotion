package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Service.PagingService;
import com.xhadl.yournotion.Service.SurveyService;
import com.xhadl.yournotion.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private UserService userService;
    @Autowired
    private PagingService pagingService;

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
    public String surveyList(@PageableDefault(size=10) Pageable pageable, Model model){
        List<SurveyDTO> surveys = surveyService.getSurveyList(pageable);
        if(surveys.size()==0) // 유저가 정해진 페이지를 초과해 접근하려하면 첫 페이지로 되돌림
            return "redirect:/surveyList";
        model.addAttribute("surveys", surveys);
        /* pageRange : [startPage], [endPage], [summaryPage] */
        model.addAttribute("pageRange", pagingService.getSurveyPageRange(pageable.getPageNumber()));
        return "/survey/surveyList";
    }

    @GetMapping("/survey/detail/{id}")
    public String surveyDetail(@PathVariable int id, Model model){
        surveyService.setSurveyDetail(model , id);
        model.addAttribute("isParticipated",surveyService.isParticipatedSurvey(id));
        return "/survey/detail";
    }

    @PostMapping("/survey/wantSurvey")
    @ResponseBody
    public Boolean wantSurvey(@RequestParam("id") int surveyId){
        return surveyService.addSurveyWant(surveyId);
    }
}
