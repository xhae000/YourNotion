package com.xhadl.yournotion.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "options")
public class OptionEntity {

    public OptionEntity(String option_des, int question_id){
        this.option_des = option_des;
        this.questionId = question_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String option_des;

    @Column(name = "question_id")
    int questionId;
}
