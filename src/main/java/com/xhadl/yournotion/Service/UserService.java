package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.DTO.LoginDTO;
import com.xhadl.yournotion.DTO.TokenDTO;
import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface UserService {
    public Optional<UserEntity> getOptionalUserInfoByUsername (String username);

    public void joinUser(UserDTO userDTO);

    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO, AuthenticationManagerBuilder authenticationManagerBuilder,
                                          HttpServletResponse response);
}
