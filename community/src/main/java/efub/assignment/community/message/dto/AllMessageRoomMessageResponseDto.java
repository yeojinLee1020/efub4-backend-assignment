package efub.assignment.community.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllMessageRoomMessageResponseDto {
    private Long messageRoomId;
    private Long opponentId;
    private List<OneMessageRoomMessageResponseDto> messageList;
}
