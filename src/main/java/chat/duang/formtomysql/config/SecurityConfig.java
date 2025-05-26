package chat.duang.formtomysql.config;

import chat.duang.formtomysql.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用默认的 CSRF、Session 管理改为 Stateless（JWT 无 session）
                .csrf().disable()
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 2. 静态资源、注册/登录接口和页面都放行
                .authorizeHttpRequests(authz -> authz
                        .antMatchers(
                                "/", "/index.html",
                                "/login", "/login.html",
                                "/register", "/register.html",
                                "/css/**", "/js/**", "/gif/**", "/Icon/**",
                                "/api/auth/**"    // 注册 / 登录 API
                        ).permitAll()
                        // 3. 其余 /lobby/**、/chat/**、/api/lobby/**、/api/chat/** 都需要认证
                        .antMatchers("/lobby/**", "/chat/**", "/api/lobby/**", "/api/chat/**")
                        .authenticated()
                        // 4. 其他接口视需求决定放行或拒绝
                        .anyRequest().permitAll()
                )

                // 5. 添加我们自定义的 JWT Filter：在用户名/密码过滤器之前运行
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // 6. 登录失败时，对于 API 返回 401，而不是 Spring 的默认重定向；
                //    对于页面访问未登录时，返回 302 重定向到 /login
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );

        return http.build();
    }
}
