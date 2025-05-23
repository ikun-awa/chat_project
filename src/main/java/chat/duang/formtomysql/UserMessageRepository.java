package chat.duang.formtomysql;

import chat.duang.formtomysql.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
    Optional<UserMessage> findByUsername(String username);
}

