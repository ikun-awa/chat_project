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
        // 构造一个相对重定向的 EntryPoint
        LoginUrlAuthenticationEntryPoint entryPoint =
                new LoginUrlAuthenticationEntryPoint("/login");
        // 一定要设为 true，才能输出 "/login?error" 而非 "http://host/login?error"
        entryPoint.setRedirectContextRelative(true);

        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/", "/index.html",
                                "/register/**", "/login/**", "/favicon.ico"
                        ).permitAll()
                        .requestMatchers("/lobby/**", "/chat/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/login")
                        .defaultSuccessUrl("/lobby")
                        .failureUrl("/login?error")    // 登录失败时仍是相对路径
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(ex -> ex
                        // 用我们改造后的 entryPoint
                        .authenticationEntryPoint(entryPoint)
                );

        return http.build();
    }
}
