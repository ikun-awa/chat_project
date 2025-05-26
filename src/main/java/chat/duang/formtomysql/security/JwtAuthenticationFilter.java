package chat.duang.formtomysql.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService uds;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   CustomUserDetailsService uds) {
        super(authenticationManager -> {}); // 占位，不使用默认的 manager
        this.jwtUtil = jwtUtil;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws java.io.IOException, javax.servlet.ServletException {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                var userDetails = uds.loadUserByUsername(username);
                var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, res);
    }
}
