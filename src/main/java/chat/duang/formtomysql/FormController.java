package chat.duang.formtomysql;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.entity.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class FormController {

    @Autowired
    private UserMessageRepository repo;

    // 显示表单页面
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    // 处理表单提交：新增 password, gender, age, comment
    @PostMapping("/submit")
    @ResponseBody
    public String handleSubmit(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam Gender gender,
                               @RequestParam Integer age,
                               @RequestParam String comment) {
        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setPassword(password);
        msg.setGender(gender);
        msg.setAge(age);
        msg.setComment(comment);
        repo.save(msg);
        return "已保存：用户=" + username
                + "，密码=" + password
                + "，性别=" + gender
                + "，年龄=" + age
                + "，评论=" + comment;
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        Optional<UserMessage> userOpt = repo.findByUsername(username);
        if (userOpt.isPresent()) {
            UserMessage user = userOpt.get();
            // 简单明文比对（生产环境应使用加密比对）
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok("登录成功");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密码错误");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
    }
}


