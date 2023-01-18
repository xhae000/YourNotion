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
import org.springframework.ui.Model;

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

        // 클라이언트에서의 토큰 삭제
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("token")) {
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        // 이전에 왔던 페이지로 돌려보내기
        return referer != null ? referer.replaceAll("http://localhost8080","") : "/";
    }

    @Override
    public String setLogin(String requestUri, HttpServletRequest request){
        String redirect;
        // 이전페이지 uri
        String referer = request.getHeader("referer");

        /* 401 unauthorized 에러를 통해 로그인 페이지에 들어온 유저라면 기존에 가려했던 url로 보내고
           로그인 버튼 눌러서 온 유저는 이전 페이지로 보냄
        * */
        if (requestUri != null)
            redirect = requestUri;
        else if(referer != null)
            redirect = referer.replaceAll("http://localhost:8080","");
        else // 처음으로 방문한 페이지가 로그인 페이지라면 referer header 없음
            redirect = "/";

        return redirect;
    }

    @Override
    @DisplayName("설문 참여 자격 판단을 위해 view에 유저의 나이와 성별 전달")
    public void addAgeGenderModel(Model model,Authentication auth){
        if (auth != null) { // 회원
            SurveyAvailableEntity userInfo = surveyAvailableRepository.findByUsername(auth.getName());

            model.addAttribute("user_age", userInfo.getFormatAge());
            model.addAttribute("user_gender", userInfo.getGender());
        }
    }

    @Override
    public String findNicknameById(int userId){
        return userRepository.findById(userId).getNickname();
    }

}
