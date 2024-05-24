package efub.assignment.community.message.dto;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDto {

    @NotBlank(message = "보내는 사람 id는 필수입니다.")
    private String senderId;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public Message toEntity(MessageRoom messageRoom, Member sender){
        return Message.builder()
                .messageRoom(messageRoom)
                .sender(sender)
                .content(this.content)
                .build();
    }
}
