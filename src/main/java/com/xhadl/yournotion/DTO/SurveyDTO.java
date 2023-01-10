package com.xhadl.yournotion.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {
    String title;
    String des;
    String gender;
    int maker_id;
    int start_age;
    int end_age;
    String time;

}
