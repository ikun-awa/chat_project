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
                // 关闭 CSRF（根据项目需求，可酌情开启）
                .csrf().disable()

                // 1. URL 授权配置
                .authorizeRequests()
                // 以下路径无需认证，任意用户（未登录/已登录）都可访问
                .antMatchers(
                        "/",                       // 根目录
                        "/index.html",             // 门面首页
                        "/register/**",            // 注册页及其静态资源
                        "/login/**",               // 登录页及其静态资源
                        "/favicon.ico"             // 图标
                ).permitAll()
                // 访问 /lobby/** 和 /chat/** 必须认证（登录）
                .antMatchers("/lobby/**", "/chat/**").authenticated()
                // 其他所有请求一律放行（根据需要也可以改为 .denyAll()）
                .anyRequest().permitAll()
                .and()

                // 2. 登录配置：指定自定义登录页、处理 URL、成功后跳转
                .formLogin()
                .loginPage("/login")                   // 用户访问需要登录的页面时，会被重定向到这里
                .loginProcessingUrl("/api/auth/login") // 提交用户名、密码的处理接口
                .defaultSuccessUrl("/lobby", true)     // 登录成功后总是跳转到 /lobby
                .permitAll()
                .and()

                // 3. 注销配置：登出后跳回首页
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()

                // 4. 异常处理：未认证时不返回 403，而是重定向到 /login
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        return http.build();
    }
}
