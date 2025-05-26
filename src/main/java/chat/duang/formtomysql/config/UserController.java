package chat.duang.formtomysql.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtTokenProvider;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserMessageRepository userRepo;

    /**
     * GET /api/users/me
     * <p>
     * 从 Authorization header 中取出 Bearer token，
     * 校验并解析出用户名后，返回该用户的基本信息。
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String username = jwtTokenProvider.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        UserMessage user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        Map<String, Object> profile = Map.of(
                "id",       user.getId(),
                "username", user.getUsername(),
                "gender",   user.getGender(),
                "age",      user.getAge(),
                "comment",  user.getComment()
        );
        return ResponseEntity.ok(profile);
    }
}
