package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageRoomStatusResponseDto{
    private Long messageId;

    @Builder
    public MessageRoomStatusResponseDto(MessageRoom messageRoom){
        if(messageRoom == null){
            this.messageId = null;
        }
        else {
            this.messageId = messageRoom.getMessageRoomId();
        }
    }
}
