package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.Repository.SurveyRepository;
import com.xhadl.yournotion.Service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Override
    public String loadIndex(Model model) {
        model.addAttribute("surveyCount", surveyRepository.countBy());
        return "/index";
    }

}
