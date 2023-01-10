package com.xhadl.yournotion.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OptionValidator {

    @Autowired
    private CommonValidator commonValidator;

    public Boolean validate(List<String> options) {
        for (int i = 0; i < options.size(); i++)
            if (!commonValidator.validate_size(options.get(i), 1, 50))
                return false;

        return true;
    }

}
