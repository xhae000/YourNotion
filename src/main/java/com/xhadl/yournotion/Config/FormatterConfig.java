package com.xhadl.yournotion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;

@Configuration
public class FormatterConfig {
    @Bean
    public DecimalFormat decimalFormatter(){
        return new DecimalFormat("###,###");
    }
}
