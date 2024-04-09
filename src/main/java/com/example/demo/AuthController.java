package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String createJWT(@RequestBody UserEntity user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        claims.put("email",user.getUsername());
        String token = jwtUtil.generateToken(claims);
        return token;
    }


    @PreAuthorize("hasAnyRole('ROLE_member')")
    @GetMapping("/hello")
    public String hello(){
        return "hello security!!!";
    }

    @GetMapping("/username")
    public String getUser(@RequestParam("id") Long id){
        String name = userRepository.findById(id).orElseThrow().getName();
        return name;
    }


    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody UserEntity user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody UserEntity user){
        return authService.login(user.getUsername(), user.getPwd());
    }
}
