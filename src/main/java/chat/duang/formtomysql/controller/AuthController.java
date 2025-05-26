package chat.duang.formtomysql.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserMessageRepository userRepo;

    public AuthController(UserMessageRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserMessage dto) {
        boolean exists = userRepo.existsByUsername(dto.getUsername());
        if (exists) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "用户名已存在"));
        }
        userRepo.save(dto);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> creds) {
        // 验证逻辑...
        boolean ok = /* 自行实现验证 */;
        if (!ok) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "用户名或密码错误"));
        }
        String token = /* 生成 JWT */;
        return ResponseEntity.ok(Map.of("success", true, "token", token));
    }
}
