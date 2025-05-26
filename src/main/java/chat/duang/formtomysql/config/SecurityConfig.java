package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import chat.duang.formtomysql.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（若使用 JWT，可根据需求调整）
                .csrf().disable()
                // 授权配置
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/css/**",
                        "/js/**",
                        "/Bootstrap/**",
                        "/gif/**",
                        "/Icon/**",
                        "/img/**",
                        "/jQuery/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                // 自定义登录页面
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                // 注销配置
                .logout()
                .permitAll();

        // 添加 JWT 过滤器到 Spring Security 过滤链
        http.addFilterBefore(
                jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
