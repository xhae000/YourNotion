package com.xhadl.yournotion.Formatter;

import org.junit.jupiter.api.DisplayName;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class UserFormatter {

    @DisplayName("yyyymmdd -> age (한국 나이) ")
    public int formatAge(String yyyymmdd) {
        int birth = Integer.parseInt(yyyymmdd.substring(0,4));
        return LocalDate.now().getYear()-birth+1;
    }

}
