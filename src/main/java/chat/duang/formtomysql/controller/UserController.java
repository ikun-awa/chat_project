package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserMessageRepository userRepo;

    @GetMapping("/api/auth/me")
    public Map<String, Object> me(Authentication auth) {
        // 1. 从 Authentication 里拿当前用户名
        String username = auth.getName();

        // 2. 查询完整的 UserMessage 实体
        UserMessage user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // 3. 返回包括 age 和 gender
        return Map.of(
                "username", user.getUsername(),
                "gender",   user.getGender(),
                "age",      user.getAge(),
                "description", user.getComment()
        );
    }
}
