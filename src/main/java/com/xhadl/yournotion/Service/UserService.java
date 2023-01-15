package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.LoginDTO;
import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface UserService {
    public Optional<UserEntity> getOptionalUserInfoByUsername (String username);

    public void joinUser(UserDTO userDTO);
    public String setLogin(String requestUri, HttpServletRequest request);
    public String login(LoginDTO loginDTO, AuthenticationManagerBuilder authenticationManagerBuilder,
                                          HttpServletResponse responsem, HttpServletRequest request);
    public String logout(HttpServletRequest request, HttpServletResponse response);
    public void addAgeGenderModel(Model model, Authentication auth);
}
