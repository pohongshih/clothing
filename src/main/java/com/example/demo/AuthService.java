package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Map<String,Object> register(UserEntity user){
        boolean IsDuplicated = userRepository.existsUserEntitiesByEmail(user.getUsername());
        Map<String,Object> map = new HashMap<>();
        if (IsDuplicated){
            map.put("status",false);
            map.put("message","帳號重複");
        }else{
            user.setPwd(passwordEncoder().encode(user.getPassword()));
            userRepository.save(user);


            // 使用 ModelMapper 将 UserEntity 映射到 DTO
            ModelMapper modelMapper = new ModelMapper();
            UserEntityDTO userEntityDTO = modelMapper.map(user, UserEntityDTO.class);

            map.put("Info",userEntityDTO);
            map.put("token",jwtUtil.generateToken(createClaims(user)));
            map.put("status",true);
        }
        return map;
    }


    public Map<String,Object> login(String username,String password){
        Optional<UserEntity> user = userRepository.findByUsername(username);
        Map<String,Object> map = new HashMap<>();
        if(user.isPresent()){
            if(passwordEncoder().matches(password,user.get().getPassword())){
                map.put("token",jwtUtil.generateToken(createClaims(user.get())));
                map.put("status",true);
            }else{
                map.put("status",false);
                map.put("message","密碼錯誤");
            }
        }else{
            map.put("status",false);
            map.put("message","無此帳號");
        }
        return map;
    }

    public Map<String, Object> createClaims(UserEntity user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        claims.put("name",user.getName());
        claims.put("email",user.getEmail());
        claims.put("line",user.getLine());
        return claims;
    }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
