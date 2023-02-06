package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.Repository.SurveyRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import com.xhadl.yournotion.Service.SurveyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SurveyResultServiceImpl implements SurveyResultService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public boolean isMadeByUser(int surveyId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userRepository.getUserId(userDetails.getUsername());

        return surveyRepository.findById(surveyId).getMakerId() == userId;
    }
}
