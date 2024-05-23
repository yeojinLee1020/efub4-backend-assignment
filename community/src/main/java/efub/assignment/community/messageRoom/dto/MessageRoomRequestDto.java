package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomRequestDto {

    @NotBlank(message = "해당 게시글 Id는 필수입니다.")
    private String postId;

    @NotBlank(message = "보내는 사람 Id는 필수입니다.")
    private String firstSenderId;

    @NotBlank(message = "받는 사람 Id는 필수입니다.")
    private String firstRecipientId;

    @NotBlank(message = "첫쪽지 내용은 필수입니다.")
    private String content;

    public MessageRoom toEntity(Post post, Member sender, Member recipient){
        return MessageRoom.builder()
                .post(post)
                .firstSender(sender)
                .firstRecipient(recipient)
                .content(this.content)
                .build();
    }

}
