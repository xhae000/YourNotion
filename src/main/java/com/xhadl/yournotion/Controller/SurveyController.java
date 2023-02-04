package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.DTO.QuestionListDTO;
import com.xhadl.yournotion.DTO.SurveyDTO;
import com.xhadl.yournotion.Service.PagingService;
import com.xhadl.yournotion.Service.RedisService;
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
    @Autowired
    private RedisService redisService;

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
        return "redirect:/survey/detail/"+survey_id;
    }

    @GetMapping("/surveyList")
    public String surveyList(
            @PageableDefault(size=10) Pageable pageable,
            Model model,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword){
        /* 검색 시, 공백 유무 무관 */

        List<SurveyDTO> surveys = surveyService.getSurveyList(pageable, keyword);
        if(surveys.size()==0)
            return "/survey/no_surveyList";

        model.addAttribute("surveys", surveys);
        /* pageRange : [startPage], [endPage], [summaryPage] */
        model.addAttribute("pageRange", pagingService.getSurveyPageRange(pageable.getPageNumber()));
        return "/survey/surveyList";
    }

    @GetMapping("/survey/detail/{id}")
    public String surveyDetail(@PathVariable int id, Model model){
        surveyService.setSurveyDetail(model , id);
        // 조회수 처리
        redisService.increaseSeeCount(id);

        model.addAttribute("isParticipated",surveyService.isParticipatedSurvey(id));
        model.addAttribute("seeCount",redisService.increaseSeeCount(id));

        return "/survey/detail";
    }

    @PostMapping("/survey/wantSurvey")
    @ResponseBody
    public Boolean wantSurvey(@RequestParam("id") int surveyId){
        return surveyService.addSurveyWant(surveyId);
    }

    @GetMapping("/survey/myWant")
    public String myWant(){
        return "";
    }

    @GetMapping("/survey/mySurvey")
    public String mySurvey(){
        return "/survey/mySurvey";
    }
}
