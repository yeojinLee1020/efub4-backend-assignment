package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomResponseDto {
    private Long messageRoomId;
    private Long firstSenderId;
    private Long firstRecipientId;
    private String content;
    private LocalDateTime createdDate;

    public static MessageRoomResponseDto from(MessageRoom messageRoom, Long firstSenderId, Long firstRecipientId) {
        return new MessageRoomResponseDto(
                messageRoom.getMessageRoomId(),
                firstSenderId,
                firstRecipientId,
                messageRoom.getContent(),
                messageRoom.getCreatedDate()
        );
    }
}
