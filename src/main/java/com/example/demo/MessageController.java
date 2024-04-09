package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages")
    public Map<String,Object> getMessage(@RequestParam("sender") String sender,@RequestParam("receiver") String receiver){
        Map<String,Object> map = new HashMap<>();
        map.put("data",messageRepository.loadmessage(sender,receiver));
        map.put("status",true);
        map.put("message","成功載入訊息!");
        return map;
    }

    @PostMapping("/save")
    public Map<String,Object> saveMessage(@RequestBody MessageEntity messageEntity){
        Map<String,Object> map = new HashMap<>();
        map.put("data",messageRepository.save(messageEntity));
        map.put("status",true);
        map.put("message","存入訊息!");
        return map;
    }

    @GetMapping("/recent")
    public Map<String,Object> recent(@RequestParam("id") String username){
//        List<MessageEntity> receivers = messageRepository.recent(username);
//        List<MessageEntity> senders = messageRepository.recent2(username);

        List<String> all= messageRepository.recentCombined(username);
        List<String> uniqueList = all.stream().distinct().collect(Collectors.toList());
        List<Map<String, Object>> userList = new ArrayList<>();

        for (String value : uniqueList) {
            Optional<UserEntity> userOptional = userRepository.findById(Long.valueOf(value));
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                Map<String, Object> usermap = new HashMap<>();
                usermap.put("id", user.getId());
                usermap.put("name", user.getName());
                usermap.put("username", user.getUsername());
                usermap.put("line", user.getLine());
                usermap.put("email", user.getEmail());
                userList.add(usermap);
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("user",userList);
        map.put("status",true);
        return map;
    }
}
