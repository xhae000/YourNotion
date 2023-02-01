package com.xhadl.yournotion.DTO;

import com.xhadl.yournotion.Entity.OptionEntity;
import com.xhadl.yournotion.Entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class QuestionFormDTO {

    public QuestionFormDTO (QuestionEntity question, List<OptionEntity> options){
        this.question = question.getQuestion();
        this.question_type = question.getQuestionType();
        this.image = question.getImage();

        if(question.getQuestionType().equals("obj")) {
            this.options = new ArrayList<>();
            for (OptionEntity option : options)
                this.options.add(option.getOption_des());
        }

    }

    String question;
    String question_type;
    String image;
    List<String> options;


}
