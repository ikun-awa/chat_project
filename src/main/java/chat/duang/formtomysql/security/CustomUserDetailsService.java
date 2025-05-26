package chat.duang.formtomysql.security;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMessageRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMessage u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return User.withUsername(u.getUsername())
                .password("{noop}" + u.getPassword())  // 明文示例，生产请加密
                .authorities("USER")
                .build();
    }
}
