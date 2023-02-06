package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.MySurveyDTO;
import com.xhadl.yournotion.Entity.SurveyParticipantEntity;
import com.xhadl.yournotion.Repository.SurveyParticipantRepository;
import com.xhadl.yournotion.Repository.SurveyRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import com.xhadl.yournotion.Service.MySurveyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MySurveyServiceImpl implements MySurveyService {

    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyParticipantRepository surveyParticipantRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<MySurveyDTO> getParticipatedSurveys() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userRepository.getUserId(userDetails.getUsername());
        List<Integer> surveyIdList = surveyParticipantRepository.findByParticipantId(userId).stream()
                .map(SurveyParticipantEntity::getSurveyId).toList();

        return surveyRepository.getParticipatedSurveys(surveyIdList);
    }

    @Override
    public List<MySurveyDTO> getMadeSurveys() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = userRepository.getUserId(userDetails.getUsername());

        return surveyRepository.findByMakerIdOrderByIdDesc(userId).stream()
                .map(p -> modelMapper.map(p, MySurveyDTO.class)).toList();
    }
}
