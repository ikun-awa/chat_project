package chat.duang.formtomysql;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.entity.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FormController {

    @Autowired
    private UserMessageRepository repo;

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    // 注册：直接存储明文密码
    @PostMapping("/submit")
    public ResponseEntity<String> handleSubmit(@RequestParam String username,
                                               @RequestParam String password,
                                               @RequestParam Gender gender,
                                               @RequestParam Integer age,
                                               @RequestParam String comment) {
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        Optional<UserMessage> userOpt = repo.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
    }
}






/*
package chat.duang.formtomysql;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.entity.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;

@RestController
public class FormController {

    private final UserMessageRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FormController(UserMessageRepository repo,
                          PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // 显示首页
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    // 处理注册
    @PostMapping("/submit")
    public ResponseEntity<?> handleSubmit(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam Gender gender,
                                          @RequestParam Integer age,
                                          @RequestParam String comment) {
        // 密码加密后存储
        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setPassword(passwordEncoder.encode(password));
        msg.setGender(gender);
        msg.setAge(age);
        msg.setComment(comment);
        repo.save(msg);
        return ResponseEntity.ok().body(
                Map.of("success", true, "message", "注册成功"));
    }

    // 处理登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        return repo.findByUsername(username)
                .map(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return ResponseEntity.ok(Map.of("success", true, "message", "登录成功"));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("success", false, "message", "密码错误"));
                    }
                })
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of("success", false, "message", "用户不存在")));
    }
}
*/