package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserMessageRepository userRepo;

    @GetMapping("/api/auth/me")
    public Map<String, Object> me(Authentication auth) {
        if (auth == null || auth.getName() == null || "anonymousUser".equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }

        String username = auth.getName();

        UserMessage user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + username));

        return Map.of(
                "username", user.getUsername(),
                "gender", user.getGender(),
                "age", user.getAge(),
                "description", user.getComment()
        );
    }
}
