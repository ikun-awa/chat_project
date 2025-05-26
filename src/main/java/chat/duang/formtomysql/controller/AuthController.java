package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.repository.UserMessageRepository;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserMessageRepository repo;
    private final JwtUtil jwtUtil;

    public AuthController(UserMessageRepository repo, JwtUtil jwtUtil) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        return repo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(username))))
                .orElse(ResponseEntity.status(401).body("用户名或密码错误"));
    }
}
