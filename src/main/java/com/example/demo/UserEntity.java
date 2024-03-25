package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Data
@Entity
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String pwd;
    private String email;
    private String phone;
    private String line;
    private String role;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 是否"沒有過期"
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // 是否"沒有被鎖住"，通常用於密碼多次錯誤
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //是否"沒有認證過期"
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否"啟用帳號"
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
