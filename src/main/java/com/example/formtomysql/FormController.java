package com.example.formtomysql;

import com.example.formtomysql.entity.Gender;
import com.example.formtomysql.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    @GetMapping("/form")
    public String showForm() {
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("age") Integer age,
            @RequestParam("gender") String genderStr,
            Model model) {
        // 创建用户实体并设置基本属性
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);

        // 将前端的字符串转换为枚举类型
        Gender gender;
        try {
            gender = Gender.valueOf(genderStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // 转换失败，返回表单并提示错误信息
            model.addAttribute("errorMessage", "无效的性别值: " + genderStr);
            return "form";
        }
        user.setGender(gender);

        // 将用户对象发送到结果页面
        model.addAttribute("user", user);
        return "result";
    }
}






/*
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
*/

