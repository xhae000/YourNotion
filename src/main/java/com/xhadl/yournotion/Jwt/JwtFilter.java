package com.xhadl.yournotion.Jwt;


import org.junit.jupiter.api.DisplayName;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    @DisplayName("토큰의 인증 정보를 실제 SecurityContext에 저장")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String jwt = resolveToken(httpServletRequest);

        if(hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("인증되었습니다. : "+authentication.getName());
        }
        else{
            System.out.println("Anonymous 입니다. (Token empty)");
        }

        chain.doFilter(request, response);
    }

    @DisplayName("request header에서 토큰 정보를 꺼내기")
    private String resolveToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) // 토큰 정보가 담긴 쿠키 찾기
                    return cookie.getValue();
            }   
        return null;
    }

}
