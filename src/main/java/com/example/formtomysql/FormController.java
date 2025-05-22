package com.example.formtomysql.controller;

import com.example.formtomysql.entity.UserMessage;
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
    public String handleSubmit(
            @RequestParam String username,
            @RequestParam String password,         // 原 content 改为 password
            @RequestParam String gender,           // 新增性别
            @RequestParam int age,                 // 新增年龄
            @RequestParam(required = false) String comment // 新增备注，可选
    ) {
        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setPassword(password);
        msg.setGender(gender);
        msg.setAge(age);
        msg.setComment(comment);
        repo.save(msg);

        return String.format(
            "已保存：用户=%s，密码=%s，性别=%s，年龄=%d，备注=%s",
            username,
            password,
            gender,
            age,
            comment == null ? "" : comment
        );
    }
}


