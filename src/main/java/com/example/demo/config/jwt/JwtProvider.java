package com.example.demo.config.jwt;

import com.example.demo.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import  lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;
    @Value("$(jwt.secretRefresh)")
    private String jwtSecretRefresh;
    @Value("${jwt.expirationDateInMs}")
    private int jwtExpirationInMs; //время жизни токена доступа доступа 20мин
    @Autowired
    private UserService userService;

    //Генерация токена доступа
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)                                                       //идентификатор пользователя
                .setIssuedAt(new Date(System.currentTimeMillis()))                       //время с которого токен действителен
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) //время с которого токен не действителен
                .signWith(SignatureAlgorithm.HS512, jwtSecret)                           //алгоритм шифрования
                .compact();
    }

    //Генерация рефреш токена
    public String generateRefreshToken(String email) {
        String userName = userService.findByEmail(email).getEmailUser();
        Claims claims = Jwts.claims().setSubject(userName);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)                                  //идентификатор пользователя
                .setIssuedAt(new Date(System.currentTimeMillis()))  //время с которого токен действителен без срока истечения
                .signWith(SignatureAlgorithm.HS512, jwtSecretRefresh)      //алгоритм шифрования
                .compact();

    }

    //Проверка токена доступа
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    //Проверка рефреш токена
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretRefresh).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            expEx.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //Получение email авторизированного пользователя из токена
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    //Получение email авторизированного пользователя из токена
    public String getEmailFromRefreshToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecretRefresh).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
