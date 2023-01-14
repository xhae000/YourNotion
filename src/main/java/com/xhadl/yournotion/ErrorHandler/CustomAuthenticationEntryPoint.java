package com.xhadl.yournotion.ErrorHandler;

import org.junit.jupiter.api.DisplayName;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@DisplayName("401")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 로그인 페이지로 넘기기 (401 : unauthorized)
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        response.sendRedirect("/login?request="+requestUri);
    }

}