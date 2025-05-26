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
                .authorizeRequests()
                // 以下路径无需认证，任意用户（未登录/已登录）都可访问
                .antMatchers(
                        "/",                    // 根目录
                        "/index.html",          // 门面首页
                        "/register/**",         // 注册页及其资源
                        "/login/**",            // 登录页及其资源
                        "/favicon.ico"          // 图标
                ).permitAll()
                // 访问 /lobby/** 和 /chat/** 必须认证（登录）
                .antMatchers("/lobby/**", "/chat/**").authenticated()
                // 其他一律放行（或改成 .denyAll() 根据需要）
                .anyRequest().permitAll()
                .and()
                // 2. 登录配置：指定自定义登录页、登录处理接口、登录成功后跳转
                .formLogin()
                .loginPage("/login")                       // 用户没登录时跳转到 /login
                .loginProcessingUrl("/api/auth/login")     // 登录表单提交到此接口
                .defaultSuccessUrl("/lobby", true)         // 登录成功后始终跳转 /lobby
                .permitAll()
                .and()
                // 3. 注销配置：登出后跳回首页
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                // 4. 异常处理：未认证时重定向到 /login 而不是直接 403
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        return http.build();
    }
}
