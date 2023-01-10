package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.DTO.LoginDTO;
import com.xhadl.yournotion.DTO.TokenDTO;
import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Service.UserService;
import com.xhadl.yournotion.Validator.CommonValidator;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    public String login() {
        return "/security/login";
    }


    @PostMapping("/loginProc")
    @ResponseBody
    public ResponseEntity<TokenDTO> loginProc(
            @RequestBody LoginDTO userInfo,
            HttpServletResponse response
    ) {
        return userService.login(userInfo, authenticationManagerBuilder, response);
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

    /* 개발 중 테스트를 위해 임시로 생성하는 쿠키 삭제 기능*/
    @GetMapping("/delcookie")
    public String delCookie(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("token")) {
                c.setMaxAge(0);
                response.addCookie(c);

                System.out.println(c.getName() + " 쿠키 삭제");
            }
        }

        return "redirect:/login";
    }
    /*
    * putmapping, patchmapping 차이 :
    *   put : 전체 정보 수정
    *   patch : 일부 정보 수정
    * */
}
