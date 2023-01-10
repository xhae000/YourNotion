package com.xhadl.yournotion.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="survey")
@Builder
public class SurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String des;
    String gender;
    int maker_id;
    int start_age;
    int end_age;

}
