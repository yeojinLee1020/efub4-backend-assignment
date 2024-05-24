package efub.assignment.community.message.dto;

import efub.assignment.community.message.domain.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResponseDto {
    private Long messageRoomId;
    private Long messageId;
    private String senderId;
    private String content;
    private LocalDateTime createdDate;

    public static MessageResponseDto from(Message message, String senderId){
        return new MessageResponseDto(
                message.getMessageRoom().getMessageRoomId(),
                message.getMessageId(),
                senderId,
                message.getContent(),
                message.getCreatedDate()
        );
    }
}
