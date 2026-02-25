package chat.duang.formtomysql.config;

import chat.duang.formtomysql.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpFirewall firewall) throws Exception {
        http.setSharedObject(HttpFirewall.class, firewall);

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/", "/login", "/register",
                        "/lobby", "/chat",
                        "/css/**", "/js/**", "/Bootstrap/**",
                        "/gif/**", "/Icon/**", "/img/**",
                        "/jQuery/**", "/websockets/**",
                        "/api/auth/me",
                        "/api/**",
                        "/ws-chat/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
