package chat.duang.formtomysql.config;

import chat.duang.formtomysql.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpFirewall firewall) throws Exception {
        // 1) 注入自定义防火墙
        http.setSharedObject(HttpFirewall.class, firewall);

        // 2) 配置 URL 放行和登录逻辑
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/", "/login", "/register",       // 页面
                        "/lobby", "/chat",                // ← 新增放行这两条
                        "/css/**", "/js/**", "/Bootstrap/**",
                        "/gif/**", "/Icon/**", "/img/**",
                        "/jQuery/**",
                        "/api/auth/me",
                        "/api/**"                         // API 全部放行，内部自己校验 JWT
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        // 3) 在用户名密码过滤器前加入 JWT 过滤器
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
