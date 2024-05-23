package efub.assignment.community.messageRoom.repository;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    MessageRoom findByPostAndFirstSenderAndFirstRecipient(Post post, Member firstSender, Member firstRecipient);
}
