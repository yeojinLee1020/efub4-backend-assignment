package efub.assignment.community.messageRoom.repository;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
}
