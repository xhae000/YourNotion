package com.xhadl.yournotion.Security.UserDetail;

import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("userDetailService"  )
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity =
                userRepository.findByUsername(username);

        return optionalUserEntity
                .map(user -> createUser(username, user)) // 입력받은 username에 해당하는 사용자가 있다면, 내가만든 userdetails 객체를 생성한다.
                .orElseThrow(()->new UsernameNotFoundException(username)); // 없다면 null을 반환한다. (인증 실패)
    }

    private User createUser(String username, UserEntity userEntity){
        List<GrantedAuthority> grantedAuthority =
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole()));

        return new User(userEntity.getUsername(), userEntity.getPw(), grantedAuthority);
    }
}
