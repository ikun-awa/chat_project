package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authz -> authz
                        .antMatchers(
                                "/", "/index.html", "/login/**", "/register/**",
                                "/css/**", "/js/**", "/gif/**", "/Icon/**", "/favicon.ico",
                                "/api/auth/**"    // 放行注册/登录 REST 接口
                        ).permitAll()
                        .antMatchers("/lobby/**","/chat/**","/api/lobby/**").authenticated()
                        .anyRequest().denyAll()
                )
                // 删除下面这段，避免 /api/auth/login 被 Spring Security 接管
                // .formLogin(form -> form
                //     .loginPage("/login")
                //     .loginProcessingUrl("/api/auth/login")
                //     .defaultSuccessUrl("/lobby")
                //     .permitAll()
                // )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                );

        return http.build();
    }
}