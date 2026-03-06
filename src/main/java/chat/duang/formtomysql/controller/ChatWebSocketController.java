package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.model.ChatMessage;
import chat.duang.formtomysql.repository.chat.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat.sendMessage/{roomId}")
    public void send(@DestinationVariable Long roomId, ChatMessage incoming) {
        if (incoming == null || incoming.getContent() == null || incoming.getContent().trim().isEmpty()) {
            return;
        }

        ChatMessage message = new ChatMessage();
        message.setRoomId(roomId);
        message.setContent(incoming.getContent().trim());
        message.setSender(
                incoming.getSender() == null || incoming.getSender().trim().isEmpty()
                        ? "匿名"
                        : incoming.getSender().trim()
        );
        message.setTimestamp(LocalDateTime.now());

        ChatMessage saved = chatMessageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/group." + roomId, saved);
    }
}
