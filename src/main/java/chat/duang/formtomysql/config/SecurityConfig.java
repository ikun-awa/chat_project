package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import chat.duang.formtomysql.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 放宽默认 URL 防火墙检查（解决 RequestRejectedException 问题）
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpFirewall firewall,
                                           JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        // 注入自定义防火墙
        http.setSharedObject(HttpFirewall.class, firewall);

        http
                .csrf().disable()
                .authorizeRequests()                                           // 使用老版 DSL 以兼容 antMatchers
                .antMatchers(
                        "/", "/login", "/register",
                        "/css/**", "/js/**", "/Bootstrap/**",
                        "/gif/**", "/Icon/**", "/img/**", "/jQuery/**"
                ).permitAll()                                              // 放行静态资源与登录注册页
                .anyRequest().authenticated()                             // 其余请求需认证
                .and()
                .formLogin()
                .loginPage("/login").permitAll()                      // 自定义登录页
                .and()
                .logout().permitAll();                                    // 放行退出接口

        // 在用户名密码过滤器前加入 JWT 过滤器
        http.addFilterBefore(
                jwtAuthenticationFilter,
