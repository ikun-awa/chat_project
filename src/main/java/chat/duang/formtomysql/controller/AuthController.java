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
import org.springframework.http.HttpStatus;

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        return repo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> {
                    String token = jwtUtil.generateToken(username);
                    // 登录成功：返回 { "token": "..." }
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElseGet(() ->
                        // 登录失败：也返回 Map<String,String>
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", "用户名或密码错误"))
                );
    }
}
