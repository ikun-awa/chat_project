package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())  // 若前端不需要 CSRF，可直接禁用

            .authorizeHttpRequests(authz -> authz
                    // 放行页面和静态资源
                    .requestMatchers(
                            "/", "/index.html",
                            "/login/**", "/register/**",
                            "/lobby", "/lobby.html",      // 放行大厅页面
                            "/favicon.ico", "/css/**", "/js/**", "/gif/**", "/Icon/**"
                    ).permitAll()

                    // 放行登录/注册 API
                    .requestMatchers("/api/auth/**").permitAll()

                    // 其他 API（如 /api/lobby）都必须先登录
                    .anyRequest().authenticated()
            )

            // session 登录配置（如果仍保留 formLogin）
            .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/api/auth/login")
                    .defaultSuccessUrl("/lobby")
                    .permitAll()
            )

            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
            )

            // 未登录时重定向到自定义登录页
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            )
    ;

    return http.build();
}
