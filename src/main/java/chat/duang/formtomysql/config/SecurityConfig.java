package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/api/auth/**")
                )
                .authorizeRequests(authz -> authz
                        .antMatchers("/", "/index.html", "/register/**", "/login/**",
                                "/favicon.ico",
                                "/api/auth/login", "/api/auth/register")
                        .permitAll()
                        .antMatchers("/lobby/**", "/chat/**")
                        .authenticated()
                        .anyRequest().permitAll()
                )
                // 不启用 Spring Security 的表单登录处理
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
        ;

        return http.build();
    }
}
