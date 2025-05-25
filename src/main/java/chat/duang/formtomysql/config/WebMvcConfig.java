package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // “/” → index.html
        registry.addViewController("/").setViewName("forward:/index.html");
        // “/login” → login.html
        registry.addViewController("/login").setViewName("forward:/login/login.html");
        // “/register” → register.html
        registry.addViewController("/register").setViewName("forward:/register/register.html");
    }
}