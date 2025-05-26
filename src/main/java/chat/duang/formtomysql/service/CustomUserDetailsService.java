package chat.duang.formtomysql.service;

import chat.duang.formtomysql.entity.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserMessageRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMessage user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())       // 存明文请注意安全，这里建议改成加密存储
                .authorities("USER")
                .build();
    }
}
