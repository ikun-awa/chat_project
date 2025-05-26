package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.chat.ChatRoom;
import chat.duang.formtomysql.repository.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatRoomRestController {

    @Autowired
    private ChatRoomRepository repo;

    /** GET  /api/chat-rooms → 返回所有聊天室列表 */
    @GetMapping("/chat-rooms")
    public List<ChatRoom> listAll() {
        return repo.findAll();
    }

    /** GET  /api/lobby      → 返回大厅所需的数据（也可以直接一样返回 findAll()） */
    @GetMapping("/lobby")
    public List<ChatRoom> listLobby() {
        return repo.findAll();
    }
}