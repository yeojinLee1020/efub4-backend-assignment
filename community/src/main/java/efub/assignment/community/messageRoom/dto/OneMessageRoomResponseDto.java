package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneMessageRoomResponseDto {
    private Long messageRoomId;
//    private String latestMessageContent;
//    private LocalDateTime latestMessageTime;
    private String content;
    private LocalDateTime createdDate;


    public static OneMessageRoomResponseDto from(MessageRoom messageRoom){
        return new OneMessageRoomResponseDto(
                messageRoom.getMessageRoomId(),
                messageRoom.getContent(),
                messageRoom.getCreatedDate()
        );
    }
}
