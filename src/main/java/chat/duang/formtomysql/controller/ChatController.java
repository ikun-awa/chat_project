package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.model.ChatMessage;
import chat.duang.formtomysql.repository.chat.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/history")
    public List<ChatMessage> history(@RequestParam Long roomId) {
        return chatMessageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage incoming) {
        if (incoming.getRoomId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "roomId is required");
        }
        if (incoming.getContent() == null || incoming.getContent().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
        }

        ChatMessage msg = new ChatMessage();
        msg.setRoomId(incoming.getRoomId());
        msg.setContent(incoming.getContent().trim());
        msg.setSender(
                incoming.getSender() == null || incoming.getSender().trim().isEmpty()
                        ? "匿名"
                        : incoming.getSender().trim()
        );
        msg.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(msg);
    }
}
