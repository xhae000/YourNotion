package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.LoginDTO;
import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.SurveyAvailableEntity;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Jwt.TokenProvider;
import com.xhadl.yournotion.Repository.SurveyAvailableRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import com.xhadl.yournotion.Service.UserService;
import com.xhadl.yournotion.Validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurveyAvailableRepository surveyAvailableRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;




    @Override
    public Optional<UserEntity> getOptionalUserInfoByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void joinUser(UserDTO user){
        if(!userValidator.validate(user)) return;

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.save(userEntity);
    }

    @Override
    public String login(
            LoginDTO userInfo,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            HttpServletResponse response,
            HttpServletRequest request){

        UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPw());
        Authentication authentication;

        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        }catch(Exception e) {
            System.out.println("Login failed");
            return null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = tokenProvider.createToken(authentication);

        Cookie tokenCookie = new Cookie("token",jwtToken);
        response.addCookie(tokenCookie);


        return userInfo.getRedirect();
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response){
        String referer = request.getHeader("referer");

        // ???????????????????????? ?????? ??????
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("token")) {
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        // ????????? ?????? ???????????? ???????????????
        return referer != null ? referer.replaceAll("http://localhost8080","") : "/";
    }

    @Override
    public String setLogin(String requestUri, HttpServletRequest request){
        String redirect;
        // ??????????????? uri
        String referer = request.getHeader("referer");

        /* 401 unauthorized ????????? ?????? ????????? ???????????? ????????? ???????????? ????????? ???????????? url??? ?????????
           ????????? ?????? ????????? ??? ????????? ?????? ???????????? ??????
        * */
        if (requestUri != null)
            redirect = requestUri;
        else if(referer != null)
            redirect = referer.replaceAll("http://localhost:8080","");
        else // ???????????? ????????? ???????????? ????????? ??????????????? referer header ??????
            redirect = "/";

        return redirect;
    }

    @Override
    @DisplayName("?????? ?????? ?????? ????????? ?????? view??? ????????? ????????? ?????? ??????")
    public SurveyAvailableEntity addAgeGenderModel(Authentication auth){
        return surveyAvailableRepository.findByUsername(auth.getName());

    }

}
