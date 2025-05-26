package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.entity.user.Gender;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
public class FormController {

    @Autowired
    private UserMessageRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    /*
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

     */

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = repo.existsByUsername(username);
        return Map.of("exists", exists);
    }

    // 注册：直接存储明文密码
    @PostMapping("/api/submit")
    public ResponseEntity<String> handleSubmit(@RequestParam String username,
                                               @RequestParam String password,
                                               @RequestParam Gender gender,
                                               @RequestParam Integer age,
                                               @RequestParam String comment) {
        if (repo.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ExistingException: bad boy no using same name");
        }
        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setPassword(password);  // 明文存储
        msg.setGender(gender);
        msg.setAge(age);
        msg.setComment(comment);
        repo.save(msg);
        return ResponseEntity.ok("注册成功");
    }

    // 登录验证：明文比对
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        Optional<UserMessage> opt = repo.findByUsername(username);
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            String token = jwtUtil.generateToken(username);
            // 将 token 放入响应体或响应头
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("用户名或密码错误");
    }
}

