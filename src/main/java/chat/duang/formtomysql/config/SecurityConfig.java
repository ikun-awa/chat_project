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

    /** 放宽 URL 防火墙，避免 StrictHttpFirewall 导致的 404 拒绝 */
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpFirewall firewall) throws Exception {
        // 先注册自定义防火墙
        http.setSharedObject(HttpFirewall.class, firewall);

        // 再进行其他安全配置
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/", "/login", "/register",        // 前端页面
                        "/css/**", "/js/**", "/Bootstrap/**",
                        "/gif/**", "/Icon/**", "/img/**",
                        "/jQuery/**",
                        "/api/**"                          // 放行所有 API 调用
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();

        // 构建并返回 SecurityFilterChain
        return http.build();
    }
}
