package com.xhadl.yournotion.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class SurveyListFormatter {

    @Autowired
    DecimalFormat decimalFormatter;

    public Map<String, String> formatSession(String time){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(5,7));
        int date = Integer.parseInt(time.substring(8,10));

        cal.set(year, month-1, date);
        String startSession = formatter.format(cal.getTime());

        cal.add(Calendar.DATE, +7);
        String endSession = formatter.format(cal.getTime());

        Map<String, String> map = new HashMap<>();
        Calendar now_cal = Calendar.getInstance();

        // 설문 만료일과 현재를 비교
        String isInSession;

        if(cal.getTimeInMillis() > now_cal.getTimeInMillis()) isInSession = "true";
        else isInSession = "false";

        map.put("isInSession",isInSession);
        map.put("formatSession","조사 기간 :"+startSession+" ~ "+endSession);

        return map;
    }

    public String formatGender(String gender){
        if(gender.equals("male"))
            return "대상 성별 : 남성";
        else if (gender.equals("female"))
            return "대상 성별 : 여성";
        else
            return "대상 성별 : 상관없음";
    }
    public String formatAge(int startAge, int endAge){
        return "대상 나이 : "+startAge+"세 ~ "+endAge+"세";
    }
    public String formatParticipants(int count){
        return "참여 인원 : "+decimalFormatter.format(count);
    }
}
