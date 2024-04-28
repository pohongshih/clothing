package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/send") // 指定客戶端傳送位置
    public void handleOneMessage(@Payload MessageEntity messageEntity) {
        String sender = messageEntity.getSender();
        String receiver = messageEntity.getReceiver();
        String sname = messageEntity.getSname();
        String rname = messageEntity.getRname();
        String message = messageEntity.getMessage();
        String date = messageEntity.getTime();
        String room = "";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("sender",sender);
        responseMap.put("receiver",receiver);
        responseMap.put("sname",sname);
        responseMap.put("rname",rname);
        responseMap.put("message", message);
        responseMap.put("date", date);

        if(Integer.parseInt(sender)>=Integer.parseInt(receiver)){
            room = sender+receiver;
        }else{
            room = receiver+sender;
        }

        System.out.println(sender);
        messagingTemplate.convertAndSendToUser(receiver,"/chat", responseMap);
        messagingTemplate.convertAndSendToUser(sender,"/chat", responseMap);
        messagingTemplate.convertAndSend("/chat/"+room, responseMap);



//        return "Processed message: " + message;
    }
}
