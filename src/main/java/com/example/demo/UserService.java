package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根據使用者名稱（在這個例子中是 email）從資料庫中加載用戶信息
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        if (user == null) {
            throw new UsernameNotFoundException("使用者名稱不存在: " + username);
        }
        return user;
    }
}
