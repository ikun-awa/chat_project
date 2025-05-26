package chat.duang.formtomysql.repository.chat;

import chat.duang.formtomysql.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 将来可自定义 findByXXX
}
