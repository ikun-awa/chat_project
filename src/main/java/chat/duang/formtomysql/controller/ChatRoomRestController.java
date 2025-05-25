package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.ChatRoom;
import chat.duang.formtomysql.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomRestController {

    @Autowired
    private ChatRoomRepository repo;

    /** GET  /api/chat-rooms → 返回所有聊天室列表 */
    @GetMapping
    public List<ChatRoom> list() {
        return repo.findAll();
    }
}
