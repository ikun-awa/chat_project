package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.model.ChatMessage;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 接收客户端发送到 /app/chat.sendMessage 的消息
    @MessageMapping("/chat.sendMessage/{groupId}")
    public void sendMessage(@DestinationVariable String groupId, ChatMessage msg) {
        msg.setTimestamp(LocalDateTime.now());
        // 广播到订阅了 /topic/group.{groupId} 的所有客户端
        messagingTemplate.convertAndSend(
                "/topic/group." + groupId, msg
        );
    }
}