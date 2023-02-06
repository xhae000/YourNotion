package com.xhadl.yournotion.DTO;

import com.xhadl.yournotion.Formatter.SurveyListFormatter;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SurveyDTO {
    int id;
    String title;
    String des;
    String gender;
    int makerId;
    int startAge;
    int endAge;
    String time;
    String category;

    /* additional variable */
    String participants;
    String isInSession;


    /* format variable */
    String format_age;
    String format_time;
    String format_gender;


    public void formatForSurveyList(SurveyListFormatter formatter, int count){
        this.format_time = formatter.formatSession(this.time).get("formatSession");
        this.isInSession = formatter.formatSession(this.time).get("isInSession");

        this.format_gender = formatter.formatGender(this.gender);
        this.format_age =  formatter.formatAge(this.startAge, this.endAge);
        this.participants = formatter.formatParticipants(count);
    }

}
