package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 放宽 URL 检查，避免 StrictHttpFirewall 导致的 404 拒绝
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpFirewall firewall) throws Exception {
        http.setSharedObject(HttpFirewall.class, firewall)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/", "/login", "/register",        // 页面
                        "/css/**", "/js/**", "/Bootstrap/**",
                        "/gif/**", "/Icon/**", "/img/**",
                        "/jQuery/**",
                        "/api/**"                          // 放行所有 API
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();

        return http.build();
    }
}
