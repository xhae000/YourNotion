package com.xhadl.yournotion.Config;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public interface CommonConfig {

}
