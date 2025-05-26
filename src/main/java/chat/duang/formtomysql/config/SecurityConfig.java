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










/*
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
                // 1. 如不需要 CSRF，可禁用
                .csrf().disable()
                // 2. URL 授权配置
                .authorizeRequests()
                // —— 一定要先放行所有静态资源目录 ——
                .antMatchers(
                        "/", "/index.html",
                        "/login/**", "/register/**",
                        "/css/**", "/js/**",
                        "/gif/**", "/Icon/**",
                        "/Bootstrap/**", "/jQuery/**",
                        "/favicon.ico",
                        // 放行登录/注册的后端接口
                        "/api/auth/**"
                ).permitAll()
                // 大厅和聊天接口必须登录后才能访问
                .antMatchers("/lobby/**", "/chat/**", "/api/lobby/**").authenticated()
                // 其他未列出的全部拒绝
                .anyRequest().denyAll()
                .and()
                // 3. 表单登录
                .formLogin(form -> form
                        .loginPage("/login")
                        // 删除下面这一行，让 Spring Security 不再拦截 /api/auth/login
                        // .loginProcessingUrl("/api/auth/login")
                        .defaultSuccessUrl("/lobby")
                        .permitAll()
                )
                // 4. 注销
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

 */