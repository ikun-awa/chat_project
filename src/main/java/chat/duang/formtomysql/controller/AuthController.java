package chat.duang.formtomysql.controller;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserMessageRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserMessageRepository userRepo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@ModelAttribute UserMessage dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "This person already exist!"));
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepo.save(dto);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@ModelAttribute UserMessage form) {
        Optional<UserMessage> optionalUser = userRepo.findByUsername(form.getUsername());
        if (optionalUser.isEmpty() || !passwordMatched(form.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Poor user name or password"));
        }

        String token = jwtUtil.generateToken(form.getUsername());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "username", form.getUsername()
        ));
    }

    private boolean passwordMatched(String raw, String stored) {
        return passwordEncoder.matches(raw, stored) || raw.equals(stored);
    }
}
