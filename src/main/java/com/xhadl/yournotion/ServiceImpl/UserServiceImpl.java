package com.xhadl.yournotion.ServiceImpl;

import com.xhadl.yournotion.DTO.LoginDTO;
import com.xhadl.yournotion.DTO.TokenDTO;
import com.xhadl.yournotion.DTO.UserDTO;
import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Jwt.TokenProvider;
import com.xhadl.yournotion.Repository.UserRepository;
import com.xhadl.yournotion.Service.UserService;
import com.xhadl.yournotion.Validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TokenProvider tokenProvider;




    @Override
    public Optional<UserEntity> getOptionalUserInfoByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void joinUser(UserDTO user){
        if(!userValidator.validate(user)) return;

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userRepository.save(userEntity);
    }

    public ResponseEntity<TokenDTO> login(
            LoginDTO userInfo,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            HttpServletResponse response){
        UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPw());


        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = tokenProvider.createToken(authentication);

        Cookie tokenCookie = new Cookie("token",jwtToken);
        response.addCookie(tokenCookie);


        return new ResponseEntity<>(new TokenDTO(jwtToken), HttpStatus.OK);
    }


}
