package chat.duang.formtomysql.repository.chat;

import chat.duang.formtomysql.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 如果将来要按 name 查找可以加：
    // Optional<ChatRoom> findByName(String name);
}
