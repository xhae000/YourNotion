package com.xhadl.yournotion.Entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name="user")
public class SurveyAvailableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    String gender;
    String age;

    public int getFormatAge(){
        int birthYear = Integer.parseInt(this.age.substring(0,4));
        int nowYear = LocalDate.now().getYear();

        return nowYear - birthYear + 1;
    }
}
