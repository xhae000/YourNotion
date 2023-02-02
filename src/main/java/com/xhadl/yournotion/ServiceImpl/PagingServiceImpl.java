package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.Repository.SurveyRepository;
import com.xhadl.yournotion.Service.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagingServiceImpl implements PagingService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public int[] getSurveyPageRange(int nowPage) {
        int surveyCount = surveyRepository.countBy();
        int pageCount = (int) Math.ceil((float)surveyCount/10);
        int startPage = nowPage - (nowPage%10) + 1;
        int endPage = Math.min(nowPage + 9, pageCount);

        return new int[]{startPage, endPage, pageCount};
    }
}
