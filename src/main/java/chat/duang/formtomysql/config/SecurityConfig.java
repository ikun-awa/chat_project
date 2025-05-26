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
                // 1. CSRF 忽略 /api/auth/**
                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/api/auth/**")
                )
                // 2. URL 授权：白名单 和 认证白名单
                .authorizeRequests(authz -> authz
                        // 首页、静态资源、注册登录接口无须登录
                        .antMatchers(
                                "/", "/index.html",
                                "/register/**", "/login/**",
                                "/favicon.ico",
                                "/api/auth/login", "/api/auth/register"
                        ).permitAll()
                        // /lobby/** 和 /chat/** 需要登录
                        .antMatchers("/lobby/**", "/chat/**").authenticated()
                        // 其他请求按需
                        .anyRequest().permitAll()
                )
                // 3. 表单登录配置：自定义登录页、登录提交接口、登录后跳转
                .formLogin(form -> form
                        .loginPage("/login")                   // 前端登录页 GET
                        .loginProcessingUrl("/api/auth/login")  // 登录表单提交 POST
                        .defaultSuccessUrl("/lobby", true)      // 登录成功跳转
                        .permitAll()
                )
                // 4. 登出配置：登出后回首页
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                // 5. 未登录时，重定向到 /login
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login")
                        )
                );

        return http.build();
    }
}
