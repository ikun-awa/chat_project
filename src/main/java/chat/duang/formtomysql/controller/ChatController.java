package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.model.ChatMessage;
import chat.duang.formtomysql.repository.chat.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // （你原来可能已经有的拉取历史记录接口）
    @GetMapping("/history")
    public List<ChatMessage> history() {
        return chatMessageRepository.findAll();
    }

    // —— 就在这里添加 ——
    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage incoming) {
        ChatMessage msg = new ChatMessage();
        msg.setContent(incoming.getContent());
        msg.setSender(incoming.getSender());
        // 设置发送时间戳
        msg.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(msg);
    }
    // —— 添加结束 ——

    // （如果有其他接口也放在这里）
}
