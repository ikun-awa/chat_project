package chat.duang.formtomysql.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserMessageRepository userRepo;

    public AuthController(UserMessageRepository userRepo) {
        this.userRepo = userRepo;
    }

    /** 注册接口，接收 multipart/form-data 表单 */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@ModelAttribute UserMessage dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "用户名已存在"));
        }
        userRepo.save(dto);
        return ResponseEntity.ok(Map.of("success", true));
    }

    /** 登录接口，接收 multipart/form-data 表单 */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@ModelAttribute UserMessage form) {
        // findByUsername 返回 Optional<UserMessage>
        Optional<UserMessage> optionalUser = userRepo.findByUsername(form.getUsername());
        if (optionalUser.isEmpty() ||
                !optionalUser.get().getPassword().equals(form.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "用户名或密码错误"));
        }

        // TODO: 在此处生成真实 JWT 并返回
        String token = "PLACEHOLDER_TOKEN";

        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token
        ));
    }
}
