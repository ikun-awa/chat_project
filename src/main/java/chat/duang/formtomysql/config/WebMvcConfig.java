package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 根路径 → index.html
        registry.addViewController("/").setViewName("forward:/index.html");

        // 登录页 → static/login/login.html
        registry.addViewController("/login").setViewName("forward:/login/login.html");

        // 注册页 → static/register/register.html
        registry.addViewController("/register").setViewName("forward:/register/register.html");

        // 聊天大厅 → static/lobby/lobby.html
        registry.addViewController("/lobby").setViewName("forward:/lobby/lobby.html");

        // 聊天页面 → static/chat/chat.html
        registry.addViewController("/chat").setViewName("forward:/chat/chat.html");
    }
}