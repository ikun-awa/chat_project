package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.entity.Gender;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtTokenProvider jwtProvider;
    @Autowired private UserMessageRepository repo;

    // 注册
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Gender gender,
            @RequestParam Integer age,
            @RequestParam String comment) {

        if (repo.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("用户名已存在");
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

    // 登录，返回 JWT
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam String username,
            @RequestParam String password) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = jwtProvider.createToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
