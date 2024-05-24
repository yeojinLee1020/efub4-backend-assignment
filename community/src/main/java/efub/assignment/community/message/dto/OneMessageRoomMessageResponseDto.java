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
public class OneMessageRoomMessageResponseDto {
    private String content;
    private LocalDateTime createdTime;
    private Boolean sendOrNot;

    public static OneMessageRoomMessageResponseDto from(Message message, Boolean sendOrNot){
        return new OneMessageRoomMessageResponseDto(
                message.getContent(),
                message.getCreatedDate(),
                sendOrNot
        );
    }
}
