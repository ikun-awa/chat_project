package chat.duang.formtomysql.config;

import chat.duang.formtomysql.model.ChatRoom;
import chat.duang.formtomysql.repository.chat.ChatRoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatBootstrapConfig {

    @Bean
    public CommandLineRunner initRooms(ChatRoomRepository roomRepository) {
        return args -> {
            if (roomRepository.count() == 0) {
                ChatRoom room = new ChatRoom();
                room.setName("General");
                room.setDescription("Default chat room");
                roomRepository.save(room);
            }
        };
    }
}
