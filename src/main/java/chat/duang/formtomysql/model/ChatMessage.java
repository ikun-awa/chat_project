package chat.duang.formtomysql.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room_1")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 消息内容
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 发送人用户名
    @Column(nullable = false)
    private String sender;

    // 消息时间戳
    @Column(nullable = false)
    private LocalDateTime timestamp;

    // ----- 省略构造器、other getters/setters -----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // 新增的 timestamp getter/setter
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
