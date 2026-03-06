package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.model.ChatRoom;
import chat.duang.formtomysql.repository.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatRoomRestController {

    @Autowired
    private ChatRoomRepository repo;

    @GetMapping("/chat-rooms")
    public List<ChatRoom> listAll() {
        return repo.findAll();
    }

    @GetMapping("/lobby")
    public List<ChatRoom> listLobby() {
        return repo.findAll();
    }

    @PostMapping("/chat-rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoom create(@RequestBody Map<String, String> payload) {
        String name = payload.getOrDefault("name", "").trim();
        String description = payload.getOrDefault("description", "").trim();

        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        if (repo.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "room name already exists");
        }

        ChatRoom room = new ChatRoom();
        room.setName(name);
        room.setDescription(description);
        return repo.save(room);
    }
}
