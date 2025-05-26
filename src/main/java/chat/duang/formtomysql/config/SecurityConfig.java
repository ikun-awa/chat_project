package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 禁用 CSRF（前后端分离场景可选）
                .csrf().disable()

                // 2. 放行静态资源
                .authorizeRequests(authz -> authz
                        // 内置静态目录
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // 如果你有自定义目录也可以加上
                        .antMatchers("/Icon/**", "/gif/**", "/Bootsrap/**",
                                "/img/**",
                                "/jQuery/**").permitAll()

                        // 3. 放行页面和登录注册相关接口
                        .antMatchers(
                                "/", "/index.html",
                                "/login/**", "/register/**",
                                "/api/auth/**"
                        ).permitAll()

                        // 4. lobby 和 chat 需要已登录
                        .antMatchers("/lobby/**", "/chat/**").authenticated()

                        // 5. 其余一律拒绝或根据需求调整
                        .anyRequest().denyAll()
                )

                // 6. 表单登录配置
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/login")
                        .defaultSuccessUrl("/lobby", true)
                        .permitAll()
                )

                // 7. 登出配置
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )

                // 8. 未认证时重定向到登录页
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                );

        return http.build();
    }
}




/*
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
                        "/api/auth/**",
                        "/Bootsrap/**",
                        "/img/**",
                        "/jQuery/**"

                        // 放行登录、注册 API
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


 */

