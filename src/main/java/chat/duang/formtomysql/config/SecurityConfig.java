package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. URL 授权配置
                .authorizeHttpRequests(authz -> authz
                        // 以下路径无需认证，任意用户（未登录/已登录）都可访问
                        .requestMatchers(
                                "/",                       // 根目录
                                "/index.html",             // 门面首页
                                "/register/**",            // 注册页及其资源
                                "/login/**",               // 登录页及其资源
                                "/favicon.ico"             // 图标
                        ).permitAll()
                        // 访问 /lobby/** 和 /chat/** 必须认证（登录）
                        .requestMatchers("/lobby/**", "/chat/**").authenticated()
                        // 其他一律放行（或根据需要改成 denyAll）
                        .anyRequest().permitAll()
                )
                // 2. 登录配置：指定自定义登录页、登录成功后默认跳转 /lobby
                .formLogin(form -> form
                        // 点击登录按钮后，前端页面应该指向 /login（GET） 来获取登录页面
                        .loginPage("/login")
                        // 表单提交处理的接口（可根据后端实际路径调整）
                        .loginProcessingUrl("/api/auth/login")
                        // 登录成功后跳转
                        .defaultSuccessUrl("/lobby")
                        .permitAll()
                )
                // 3. 注销配置：登出后跳回首页
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                // 4. 异常处理：未认证时，不返回 403，而是重定向到 /login
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                );

        return http.build();
    }
}