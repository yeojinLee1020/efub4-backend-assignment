package efub.assignment.community.messageRoom.repository;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    MessageRoom findByPostAndFirstSenderAndFirstRecipient(Post post, Member firstSender, Member firstRecipient);
    List<MessageRoom> findByFirstSender(Member firstSender);
    List<MessageRoom> findByFirstRecipient(Member firstRecipient);
    MessageRoom findByMessageRoomId(Long MessageRoomId);

}
