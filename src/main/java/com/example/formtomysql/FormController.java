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

    // 显示表单页面（如果用 Thymeleaf 或 JSP）
    @GetMapping("/")
    public String index() {
        return "forward:/chat/index.html";
    }

    // 处理表单提交
    @PostMapping("/submit")
    @ResponseBody
    public String handleSubmit(@RequestParam String username,
                               @RequestParam String content) {
        UserMessage msg = new UserMessage();
        msg.setUsername(username);
        msg.setContent(content);
        repo.save(msg);
        return "已保存：用户=" + username + "，内容=" + content;
    }
}

