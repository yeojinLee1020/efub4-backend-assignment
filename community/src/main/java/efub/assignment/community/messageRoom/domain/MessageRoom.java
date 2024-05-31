package efub.assignment.community.messageRoom.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MessageRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_room_id", updatable = false)
    private Long messageRoomId;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "first_sender_id", updatable = false)
    private Member firstSender;

    @ManyToOne
    @JoinColumn(name = "first_recipient_id", updatable = false)
    private Member firstRecipient;



    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public MessageRoom(Post post, Member firstSender, Member firstRecipient, String content) {
        this.post = post;
        this.firstSender = firstSender;
        this.firstRecipient = firstRecipient;
        this.content = content;
    }
}
