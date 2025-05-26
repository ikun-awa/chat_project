package chat.duang.formtomysql.repository;

import chat.duang.formtomysql.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 如果将来需要按聊天室分组查询，可以在这里加方法签名
    // List<ChatMessage> findByRoomIdOrderByTimestampAsc(Long roomId);
}
