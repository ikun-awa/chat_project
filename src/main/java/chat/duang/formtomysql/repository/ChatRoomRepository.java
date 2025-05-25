package chat.duang.formtomysql.repository;

import chat.duang.formtomysql.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 将来可自定义 findByXXX
}
