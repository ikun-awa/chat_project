package com.example.formtomysql.controller;

import com.example.formtomysql.entity.UserMessage;
import com.example.formtomysql.entity.Gender;
import com.example.formtomysql.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}


