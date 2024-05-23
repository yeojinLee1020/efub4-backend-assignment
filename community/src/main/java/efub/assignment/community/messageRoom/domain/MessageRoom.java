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
    @Column(name = "messageRoom_Id", updatable = false)
    private Long messageRoomId;

    @ManyToOne
    @JoinColumn(name = "firstSender_id", updatable = false)
    private Member firstSender;

    @ManyToOne
    @JoinColumn(name = "firstRecipient_id", updatable = false)
    private Member firstRecipient;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public MessageRoom(Member firstSender, Member firstRecipient, Post post, String content) {
        this.firstSender = firstSender;
        this.firstRecipient = firstRecipient;
        this.post = post;
        this.content = content;
    }
}
