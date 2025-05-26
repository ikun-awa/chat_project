package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.entity.user.Gender;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserMessageRepository repo;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam Gender gender,
                                           @RequestParam Integer age,
                                           @RequestParam String comment) {
        if (repo.existsByUsername(username)) {
            return ResponseEntity.status(409).body("用户名已存在");
        }
        UserMessage u = new UserMessage();
        u.setUsername(username);
        u.setPassword(password);
        u.setGender(gender);
        u.setAge(age);
        u.setComment(comment);
        repo.save(u);
        return ResponseEntity.ok("注册成功");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String user = body.get("username");
        String pass = body.get("password");
        return repo.findByUsername(user)
                .filter(u -> u.getPassword().equals(pass))
                .map(u -> {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElse(ResponseEntity
                        .status(401)
                        .body("用户名或密码错误")  // 失败：返回 String
                );
    }
}
