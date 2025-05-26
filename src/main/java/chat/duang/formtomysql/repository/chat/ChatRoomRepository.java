package chat.duang.formtomysql.repository.chat;

import chat.duang.formtomysql.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> { }