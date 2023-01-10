package com.xhadl.yournotion.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    String question_type;
    String question;
    MultipartFile image;
}
