package com.xhadl.yournotion.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class TokenProvider implements InitializingBean { /* InitializingBean을 상속받은 이유는 의존성 주입이 되고나서 키 설정을 위해*/

    private static final String AUTHORITES_KEY = "auth";

    private final String secret;
    private final long tokenValidity; // ms 단위
    private Key key;

    public TokenProvider(
            @Value("${jwt.secretKey}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidity_s
    ){
        this.secret = secret;
        this.tokenValidity = tokenValidity_s * 1000;
    }


    @Override
    @DisplayName("키 설정")
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public String createToken(Authentication authentication){
        String authorites = authentication.getAuthorities().stream()
             .map(GrantedAuthority::getAuthority)
             .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidity);

        Header header = Jwts.header();
        header.setType("JWT");

        return Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setSubject(authentication.getName())
                .claim(AUTHORITES_KEY, authorites)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token){
        try{
            // 토큰을 파싱함. 문제가 없으면 return true
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(Exception e){
            System.out.println("token has error : "+e.getMessage());
            return false;
        }
    }
}
