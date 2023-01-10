package com.xhadl.yournotion.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailValidator {
    @Autowired
    private CommonValidator commonValidator;

    public Boolean validate_email(String email){
        return commonValidator.validate(email,1, 32);
    }

    public Boolean validate_emailAndKey(String email, String key){
        return commonValidator.validate(email,1, 32) &&
                commonValidator.validate(key,6,6);
    }
}
