// 伪代码：ChatController.java
CLASS ChatController
    FIELD chatMessageRepository : ChatMessageRepository

    METHOD history() -> List<ChatMessage>
        RETURN chatMessageRepository.findAll()
    END METHOD

    METHOD sendMessage(incoming : ChatMessage) -> ChatMessage
        msg = new ChatMessage()
        msg.content = incoming.content
        msg.sender = incoming.sender
        msg.timestamp = LocalDateTime.now()

        RETURN chatMessageRepository.save(msg)
    END METHOD
END CLASS
