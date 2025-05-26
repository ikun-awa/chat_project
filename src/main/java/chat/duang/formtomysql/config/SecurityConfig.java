/*
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

 */





package chat.duang.formtomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // 如无需 CSRF，可禁用

                .authorizeHttpRequests(auth -> auth
                        // 放行静态资源和页面
                        .requestMatchers(
                                "/Bootstrap/**", "/css/**", "/gif/**", "/Icon/**",
                                "/img/**", "/jQuery/**", "/js/**",
                                "/", "/login/**", "/register/**"
                        ).permitAll()

                        // 放行登录/注册相关后端接口
                        .requestMatchers("/api/auth/**").permitAll()

                        // 其余请求都必须登录
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/login")
                        .defaultSuccessUrl("/lobby", true)
                        .permitAll()
                )
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




