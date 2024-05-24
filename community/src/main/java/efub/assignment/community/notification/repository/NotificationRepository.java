package efub.assignment.community.notification.repository;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByMemberId(Long memberId);

}
