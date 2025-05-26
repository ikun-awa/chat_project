package chat.duang.formtomysql.repository.user;

import chat.duang.formtomysql.entity.user.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
    Optional<UserMessage> findByUsername(String username);
    boolean existsByUsername(String username);
}

