package com.xhadl.yournotion.Config;

import com.xhadl.yournotion.ErrorHandler.CustomAccessDeniedHandler;
import com.xhadl.yournotion.ErrorHandler.CustomAuthenticationEntryPoint;
import com.xhadl.yournotion.Jwt.JwtAccessDeniedHandler;
import com.xhadl.yournotion.Jwt.JwtAuthenticationEntryPoint;
import com.xhadl.yournotion.Jwt.JwtFilter;
import com.xhadl.yournotion.Jwt.TokenProvider;
import com.xhadl.yournotion.Security.Handler.LoginFailureHandler;
import com.xhadl.yournotion.Security.Handler.LoginSuccessHandler;
import com.xhadl.yournotion.Security.UserDetail.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@ComponentScan(basePackages = {"com.xhadl.yournotion.Security.UserDetail"})
@ComponentScan(basePackages = {"com.xhadl.yournotion.Jwt"})
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private final TokenProvider tokenProvider;
    @Autowired
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;



    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);

        http
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함

                .and()
                .authorizeRequests()
                .antMatchers(("/login")).anonymous()
                .antMatchers(("/loginProc")).anonymous()
                .antMatchers(("/logoutProc")).authenticated()
                .antMatchers("/join").anonymous()
                .antMatchers("/survey/**").authenticated()
                .antMatchers("/joinProc").anonymous()    // LoadBalancer Chk
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()

                .and()
                .apply(new JwtConig(tokenProvider))

                //401, 403 error
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }
}