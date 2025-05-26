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
                // 1. 关闭 CSRF（如果不需要的话）
                .csrf().disable()

                // 2. 关闭 HTTP Basic，否则它会优先返回 401
                .httpBasic().disable()

                // 3. 授权规则
                .authorizeRequests()
                // 静态资源和登录注册页放行
                .antMatchers(
                        "/", "/index.html",
                        "/login", "/login/**",
                        "/register", "/register/**",
                        "/css/**", "/js/**", "/gif/**", "/Icon/**",
                        "/favicon.ico",
                        "/api/auth/**"       // 放行登录、注册 API
                ).permitAll()
                // 大厅和聊天接口必须登录
                .antMatchers("/lobby/**", "/chat/**", "/api/lobby/**")
                .authenticated()
                // 其他请求禁止访问（根据需求也可以改成 .permitAll()）
                .anyRequest().denyAll()
                .and()

                // 4. 表单登录
                .formLogin()
                .loginPage("/login")                   // 自定义登录页面 GET /login
                .loginProcessingUrl("/api/auth/login") // 登录表单 POST 接口
                .defaultSuccessUrl("/lobby", true)     // 登录成功默认跳转
                .permitAll()
                .and()

                // 5. 注销
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()

                // 6. 未登录时重定向到 /login
                .exceptionHandling()
                .authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")
                )
        ;

        return http.build();
    }
}
