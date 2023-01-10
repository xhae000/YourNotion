package com.xhadl.yournotion.Validator;

import org.springframework.stereotype.Component;

@Component
public class CommonValidator {

    public boolean validate(Object obj, int min, int max){
        String str = String.valueOf(obj);

        // 1. Null 및 space 검사
        if ((str.replace(" ","").length() !=
                str.length()) || str == null) {
            return false;
        }

        // 2. 길이 검사
        if(str.length()<min || str.length()>max){
            return false;
        }

        return true;

    }

    public boolean validate_size(Object obj, int min, int max){
        String str = String.valueOf(obj);
        //  길이 검사
        if(str.length()<min || str.length()>max)
            return false;

        return true;

    }
}
