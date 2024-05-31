package efub.assignment.community.message.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", updatable = false)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member sender;


    @ManyToOne
    @JoinColumn(name="message_room_id", updatable = false)
    private MessageRoom messageRoom;

    @Column(nullable = false, length = 500)
    private String content;

    @Builder

    public Message(Member sender, MessageRoom messageRoom, String content) {
        this.sender = sender;
        this.messageRoom = messageRoom;
        this.content = content;
    }
}
