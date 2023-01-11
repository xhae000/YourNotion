package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.DTO.LoginDTO;
import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Service.UserService;
import com.xhadl.yournotion.Validator.CommonValidator;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Controller
public class SecurityController {

    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    CommonValidator commonValidator;

    AuthenticationManagerBuilder authenticationManagerBuilder;

    public SecurityController(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value="request", required = false)String requestUri,
            HttpServletRequest request,
            Model model) {
        //uri
        String redirect = userService.setLogin(requestUri, request);
        model.addAttribute("redirect",redirect);

        return "/security/login";
    }


    @PostMapping("/loginProc")
    @ResponseBody
    public String loginProc(
            @RequestBody LoginDTO loginDTO,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        return userService.login(loginDTO, authenticationManagerBuilder, response,request);
    }

    @GetMapping("/join")
    public String join() {
        return "/security/join";
    }

    @PostMapping("/joinProc")
    public String joinProc(UserDTO userDTO) {

        // 비밀번호 암호화 전에 유효성 검사부터
        if (!commonValidator.validate(userDTO.getPw(), 1, 32))
            return "redirect:/";

        userDTO.setPw(passwordEncoder.encode(userDTO.getPw()));
        userService.joinUser(userDTO);

        return "redirect:/";
    }

    @DisplayName("사용 중인 ID인지 검사")
    @PostMapping("/isUsingUsername")
    @ResponseBody
    public Boolean isUsingUsername(@RequestParam("username") String username) {

        Optional<UserEntity> optionalUserEntity =
                userService.getOptionalUserInfoByUsername(username);

        return !optionalUserEntity.isEmpty();
    }

    @PostMapping("/logoutProc")
    @DisplayName("로그아웃 (토큰 삭제)")
    public String logoutProc(HttpServletRequest request, HttpServletResponse response) {

        String redirectUri = userService.logout(request, response);
        return "redirect:"+redirectUri;
    }
    /*
    * putmapping, patchmapping 차이 :
    *   put : 전체 정보 수정
    *   patch : 일부 정보 수정
    * */
}
