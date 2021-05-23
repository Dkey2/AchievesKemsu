package com.example.demo.config;

import com.example.demo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private String email;
    private String password;
    private int id;
    private String statusUser;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserToCustomUserDetails (User user) {
        CustomUserDetails cud = new CustomUserDetails();
        cud.email = user.getEmailUser();
        cud.password = user.getPasswordUser();
        cud.id = user.getIdUser();
        cud.statusUser = user.getStatusUser().getStatusUser();
        cud.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoleUser().getNameRole()));
        return cud;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getStatusUser() { return statusUser;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
