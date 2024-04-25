package com.HP50.be.global.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        // UTF8로 인코딩 하고 암호화는 HS256 방식으로 진행된다.
        // secret을 문자열로 받았기 때문에 SecretKeySpec 로 변환하는 과정이 필요
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getMemberId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().
                parseSignedClaims(token).
                getPayload()
                .get("memberId", String.class);
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().
                parseSignedClaims(token).
                getPayload()
                .get("email", String.class);
    }

    public String getMemberName(String token) {

        return Jwts.parser().verifyWith(secretKey).build().
                parseSignedClaims(token).
                getPayload()
                .get("memberName", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().
                parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createAccessJwt(Integer memberId, String email, String memberName, String memberGit, String memberImg){
        return Jwts.builder()

                // header를 따로 지정해주지 않는다면 type 이 지정되지 않음
                // jwt 를 많이 사용하니까 따로 명시해주지 않는다면 jwt 로 판단함

                .header()
                    .add("typ", "jwt")
                    .and()
                // claim는 jwt 내부에 들어갈 payload 
                .claim("memberId", memberId)
                .claim("email", email)
                .claim("memberName", memberName)
                .claim("memberGit", memberGit)
                .claim("memberImg", memberImg)
                .claim("sub",JwtConstants.ACCESS)
                .issuedAt(new Date(System.currentTimeMillis())) // jwt 발급한 시간
                .expiration(new Date(System.currentTimeMillis() + JwtConstants.ACCESS_EXP_TIME)) // jwt 만기 시간
                .signWith(secretKey) // 해당 키로 암호화를 하겠다.
                .compact(); // jwt 생성
    }

    public String createRefreshJwt(Integer memberId, String email, String memberName, String memberGit, String memberImg){
        return Jwts.builder()

                // header를 따로 지정해주지 않는다면 type 이 지정되지 않음
                // jwt 를 많이 사용하니까 따로 명시해주지 않는다면 jwt 로 판단함

                .header()
                .add("typ", JwtConstants.JWT_TYPE)
                .and()
                // claim는 jwt 내부에 들어갈 payload
                .claim("memberId", memberId)
                .claim("email", email)
                .claim("memberName", memberName)
                .claim("memberGit", memberGit)
                .claim("memberImg", memberImg)
                .claim("sub",JwtConstants.REFRESH)
                .issuedAt(new Date(System.currentTimeMillis())) // jwt 발급한 시간
                .expiration(new Date(System.currentTimeMillis() + JwtConstants.REFRESH_EXP_TIME)) // jwt 만기 시간
                .signWith(secretKey) // 해당 키로 암호화를 하겠다.
                .compact(); // jwt 생성
    }
}
