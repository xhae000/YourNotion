package com.xhadl.yournotion.DTO;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionListDTO {
    List<QuestionDTO> question;
}
