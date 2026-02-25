package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.Gender;
import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = repo.existsByUsername(username);
        return Map.of("exists", exists);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> handleSubmit(
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
                            "message", "This poor person already exits!"
                    ));
        }

        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setPassword(passwordEncoder.encode(password));
        msg.setGender(gender);
        msg.setAge(age);
        msg.setComment(comment);
        repo.save(msg);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Good person, succesese"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password) {

        Optional<UserMessage> opt = repo.findByUsername(username);
        if (opt.isPresent() && passwordMatched(password, opt.get().getPassword())) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token,
                    "username", username
            ));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "success", false,
                        "message", "Poor user name or password"
                ));
    }

    private boolean passwordMatched(String raw, String stored) {
        return passwordEncoder.matches(raw, stored) || raw.equals(stored);
    }
}
