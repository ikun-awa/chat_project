package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用 CSRF（如不需要）
                .csrf().disable()

                // 2. URL 授权
                .authorizeRequests()
                // —— 一定要先放行静态资源 ——
                .antMatchers(
                        "/", "/index.html",
                        "/login/", "/login/**",
                        "/register/", "/register/**",
                        "/css/**", "/js/**", "/gif/**", "/Icon/**",
                        "/favicon.ico",
                        // 放行登录/注册接口
                        "/api/auth/**"
                ).permitAll()
                // /lobby 和 /chat 访问需要认证
                .antMatchers("/lobby/**", "/chat/**", "/api/lobby/**").authenticated()
                // 其他一律拒绝（或改成 anyRequest().permitAll()）
                .anyRequest().denyAll()
                .and()

                // 3. 表单登录配置
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/auth/login")
                .defaultSuccessUrl("/lobby", true)
                .permitAll()
                .and()

                // 4. 登出配置
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()

                // 5. 未认证时重定向到登录页
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        return http.build();
    }
}