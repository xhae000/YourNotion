package com.xhadl.yournotion.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="survey")
@DynamicInsert // Insert 시 null인 필드 제외 (db의 default value로 저장되게끔)
@DynamicUpdate // Update 시 null인 필드 제외
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
    String category;
    String time;

    // 시간 포맷


}
