package chat.duang.formtomysql.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserMessageRepository userRepo;

    public AuthController(UserMessageRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * 注册接口
     * 使用 @ModelAttribute 以兼容 multipart/form-data 提交的 FormData
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@ModelAttribute UserMessage dto) {
        // 检查用户名是否已存在
        boolean exists = userRepo.existsByUsername(dto.getUsername());
        if (exists) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "用户名已存在"));
        }
        // 保存新用户
        userRepo.save(dto);
        return ResponseEntity.ok(Map.of("success", true));
    }

    /**
     * 登录接口
     * 使用 @ModelAttribute 以兼容 URLSearchParams(FormData) 提交
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@ModelAttribute UserMessage form) {
        // 从数据库查找用户
        UserMessage user = userRepo.findByUsername(form.getUsername());
        // 密码校验（此处示例直接比较明文，生产请务必使用加密存储和校验）
        if (user == null || !user.getPassword().equals(form.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "用户名或密码错误"));
        }
        // TODO: 在此处生成并返回真实的 JWT
        String token = "PLACEHOLDER_TOKEN";

        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token
        ));
    }
}
