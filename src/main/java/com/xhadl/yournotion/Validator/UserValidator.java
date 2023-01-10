package com.xhadl.yournotion.Validator;

import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.MailAuthEntity;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Repository.MailAuthRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidator {
    @Autowired
    private CommonValidator commonValidator;
    @Autowired
    private MailAuthRepository mailAuthRepository;
    @Autowired
    private UserRepository userRepository;

    public Boolean validate(String username){
        return commonValidator.validate(username,1,16);
    }

    public Boolean validate(UserDTO user){
        // 유저가 회원가입 과정에서 이메일 인증을 속일 수 있기에 검증
        List<MailAuthEntity> mailAuthEntities =
                mailAuthRepository.findByEmail(user.getEmail());
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());

        // 인증 되어있는 메일이야함
        if(mailAuthEntities.get(mailAuthEntities.size()-1).getIs_auth().equals("false"))
            return false;

        // 해당 메일로 생성된 게정이 없어야함
        if(userEntity != null)
            return false;

        // 성별 검사
        if(!user.getGender().equals("male") && !user.getGender().equals("female"))
            return false;

        // 나이 검사
        int yy = Integer.parseInt(user.getAge().substring(0,1));
        int mm = Integer.parseInt(user.getAge().substring(2,3));
        int dd = Integer.parseInt(user.getAge().substring(4,5));

        if(mm>12) return false;
        if(dd>31) return false;


        // 문자열 검사
        return  commonValidator.validate(user.getUsername(),1,16) &&
                commonValidator.validate(user.getNickname(),1,8);
    }
}
