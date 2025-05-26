package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 如果前端完全用 JWT，可改为 disable；否则保留 CSRF 并在必要时忽略特定路径
                .csrf(csrf -> csrf.disable())

                // 1. 授权配置
                .authorizeRequests(authz -> authz
                        // 放行首页、登录页、注册页、静态资源，以及 JWT 登录/注册 API
                        .antMatchers(
                                "/", "/index.html",
                                "/login/**", "/register/**",
                                "/lobby", "/lobby.html",
                                "/favicon.ico", "/css/**", "/js/**", "/gif/**", "/Icon/**",
                                "/api/auth/**"
                        ).permitAll()
                        // 其余所有请求都需要登录
                        .anyRequest().authenticated()
                )

                // 2. 表单登录（如仍保留 session 登录）
                .formLogin(form -> form
                        .loginPage("/login")                       // 前端登录页
                        .loginProcessingUrl("/api/auth/login")     // 登录提交的 POST API
                        .defaultSuccessUrl("/lobby", true)         // 登录后默认跳 /lobby
                        .permitAll()
                )

                // 3. 注销
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                )

                // 4. 未登录时重定向
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                )
        ;

        return http.build();
    }
}