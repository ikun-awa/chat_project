package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.entity.user.Gender;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FormController {

    @Autowired
    private UserMessageRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    // 检查用户名是否存在，返回 { exists: true/false }
    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = repo.existsByUsername(username);
        return Map.of("exists", exists);
    }

    // 注册接口，接收 multipart/form-data
    @PostMapping("/submit")
    public ResponseEntity<Map<String,Object>> handleSubmit(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Gender gender,
            @RequestParam Integer age,
            @RequestParam String comment) {

        if (repo.existsByUsername(username)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "success", false,
                            "message", "用户名已存在"
                    ));
        }

        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setPassword(password);
        msg.setGender(gender);
        msg.setAge(age);
        msg.setComment(comment);
        repo.save(msg);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "注册成功"
        ));
    }

    // 登录接口，接收 multipart/form-data
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(
            @RequestParam String username,
            @RequestParam String password) {

        Optional<UserMessage> opt = repo.findByUsername(username);
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token
            ));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "success", false,
                        "message", "用户名或密码错误"
                ));
    }
}
