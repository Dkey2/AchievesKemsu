package com.example.demo.config.jwt;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.config.CustomUserDetailsService;
import com.example.demo.controller.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

@Component
@Log
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization"; //Ключ авторизации

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilterInternal (HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token =  getTokenFromRequest((HttpServletRequest) servletRequest); //получаем токен из запроса
        String uri = servletRequest.getRequestURI();
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json;charset=UTF-8");
        if (token != null && jwtProvider.validateToken(token)) { //если токен есть и он валиден
            String email = jwtProvider.getEmailFromToken(token); //достаем email из токена
            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email); //преобразуем пользователя к CustomUserDetails
            if(customUserDetails.getStatusUser().equals("Аннигилирован")){
                servletResponse.setStatus(403);
                ErrorResponse errorResponse = writeResponse(403, "Forbidden", "Ваш аккаунт был удален без права восстановления", uri);
                servletResponse.getWriter().write(Objects.requireNonNull(toJSON(errorResponse)));
                return;
            }
            if(customUserDetails.getStatusUser().equals("Удален")){
                servletResponse.setCharacterEncoding("UTF-8");
                servletResponse.setContentType("application/json;charset=UTF-8");
                servletResponse.setStatus(403);
                ErrorResponse errorResponse = writeResponse(403, "Forbidden", "Ваш аккаунт был удален", uri);
                servletResponse.getWriter().write(Objects.requireNonNull(toJSON(errorResponse)));
                return;
            }
            if(customUserDetails.getStatusUser().equals("Забанен")){
                servletResponse.setStatus(403);
                ErrorResponse errorResponse = writeResponse(403, "Forbidden", "Ваш аккаунт был забанен", uri);
                servletResponse.getWriter().write(Objects.requireNonNull(toJSON(errorResponse)));
                return;
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities()); //определяем права пользователя
            SecurityContextHolder.getContext().setAuthentication(auth); //аутентификация
        }
        else if (token != null && !jwtProvider.validateToken(token)) {
            servletResponse.setStatus(403);
            ErrorResponse errorResponse = writeResponse(403, "Forbidden", "Используется неверный или истекший токен", uri);
            servletResponse.getWriter().write(Objects.requireNonNull(toJSON(errorResponse)));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse); //используется при каждой передаче запроса/ответа
    }

    private ErrorResponse writeResponse(int status, String error, String message, String uri) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S+00:00");
        java.util.Date dateNow = new Date();
        String strDate = sdfDate.format(dateNow);
        return new ErrorResponse(strDate,status, error, message, uri);

    }

    private String toJSON (ErrorResponse errorResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    //Получение токена из запроса.
    private String getTokenFromRequest (HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION); //Ищем в хедер пару ключ-значение с обозначенным ключом
        if (hasText(bearer) && bearer.startsWith("Bearer ")) //Проверяем, чтобы значение было записано верно
            return bearer.substring(7); //возвращаем токен (он лежит в значении, начиная с 7 символа)
        return null;
    }
}
