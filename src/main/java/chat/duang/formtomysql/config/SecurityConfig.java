package chat.duang.formtomysql.config;

import chat.duang.formtomysql.security.CustomUserDetailsService;
import chat.duang.formtomysql.security.JwtAuthenticationFilter;
import chat.duang.formtomysql.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService uds;
    private final JwtUtil jwtUtil;

    public SecurityConfig(CustomUserDetailsService uds, JwtUtil jwtUtil) {
        this.uds = uds;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(jwtUtil, uds))
                .authorizeRequests()
                .antMatchers("/api/auth/**", "/login", "/register").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
