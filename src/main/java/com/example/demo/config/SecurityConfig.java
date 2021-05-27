package com.example.demo.config;

import com.example.demo.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //т.к. авторизация по токену, не заморачиваемся с сессией
                .and()
                .authorizeRequests()
                .antMatchers("/student/*").hasRole("Студент")  //Описываем к каким запросам есть доступ у пользователей с определенной ролью
                .antMatchers("/admin/*").hasRole("Администратор")
                .antMatchers("/moderator/*").hasRole("Модератор")
                .antMatchers("/upr").hasAnyRole("Модератор", "Администратор")
                .antMatchers("/change/*", "/categories", "/rewards").hasAnyRole("Студент", "Администратор", "Модератор")
                .antMatchers("/register", "/auth", "/newToken", "/swagger*", "/education/*", "/code/*").permitAll()  //описываем какие url доступны всем пользователям независимо от роли и авторизации
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //определяем пользователя в системе
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
