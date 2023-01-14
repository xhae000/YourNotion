package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.Entity.MailAuthEntity;
import com.xhadl.yournotion.Repository.MailAuthRepository;
import com.xhadl.yournotion.Service.MailAuthService;
import com.xhadl.yournotion.Service.MailService;
import com.xhadl.yournotion.Validator.MailValidator;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MailAuthServiceImpl implements MailAuthService {
    @Autowired
    private MailAuthRepository mailAuthRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private  MailValidator mailValidator;

    @Override
    @DisplayName("In join proc")
    @Transactional
    public String setMailAuth(String mail) throws Exception {
        // input validate
        if (!mailValidator.validate_email(mail)) return "error";

        // 인증키 or using
        String result = mailService.sendSimpleMessage(mail);

        if (result .equals("using")) {
            return "using";
        }

        MailAuthEntity mailAuthEntity = new MailAuthEntity(mail, result);
        mailAuthRepository.save(mailAuthEntity);

        return "ok";
    }

    @Override
    public Boolean key_auth(String key, String email) {
        //input validate
        if(!mailValidator.validate_emailAndKey(email, key)) return false;

        List<MailAuthEntity> mailAuthEntities = mailAuthRepository.findByEmail(email);

        MailAuthEntity authEntity = mailAuthEntities.get(mailAuthEntities.size()-1);

        int id = authEntity.getId();
        String isAuth = authEntity.getIs_auth();

        if(isAuth.equals("false") && authEntity.getAuth_key().equals(key)){
            MailAuthEntity newAuthEntity = new MailAuthEntity(id, email,"true", key);
            mailAuthRepository.save(newAuthEntity);

            return true;
        }

        return false;
    }

}
