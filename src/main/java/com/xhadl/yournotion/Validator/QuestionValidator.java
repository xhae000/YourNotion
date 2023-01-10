package com.xhadl.yournotion.Validator;

import com.xhadl.yournotion.DTO.QuestionDTO;
import com.xhadl.yournotion.DTO.QuestionListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class QuestionValidator {
    @Autowired
    private CommonValidator commonValidator;
    @Value("${file.dir}/")
    private String dir;


    public ArrayList<String> validate(QuestionListDTO questions) throws IOException {
        List<QuestionDTO> question_list= questions.getQuestion();
        String savedFileName = "";

        ArrayList<String> image_name = new ArrayList<>();

        for(QuestionDTO question : question_list){

            if( !question.getQuestion_type().equals("obj") && !question.getQuestion_type().equals("sub") )
                return null;
            if(!commonValidator.validate_size(question.getQuestion(),1,100))
                return null;

            // image save on local : start

            MultipartFile file = question.getImage();

            if(!file.isEmpty()) {
                String originName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String ext = originName.substring(originName.lastIndexOf("."));

                if (!ext.equals(".jpg") && !ext.equals(".jpeg") && !ext.equals(".png"))
                    return null;

                savedFileName = uuid + ext;
                file.transferTo(new File(dir + savedFileName));

                image_name.add("/image/"+savedFileName);
                // image save on local : end
            }
            else{
                image_name.add(null);
            }
        }



        return image_name;
    }
}
