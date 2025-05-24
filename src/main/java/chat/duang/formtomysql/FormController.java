package chat.duang.formtomysql;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.entity.Gender;
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
    @PostMapping("/submit")
    public ResponseEntity<String> handleSubmit(@RequestParam String username,
                                               @RequestParam String password,
                                               @RequestParam Gender gender,
                                               @RequestParam Integer age,
                                               @RequestParam String comment) {
        if (repo.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("用户名已存在，请换一个");
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

