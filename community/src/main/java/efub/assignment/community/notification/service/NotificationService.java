package efub.assignment.community.notification.service;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.notification.domain.Notification;
import efub.assignment.community.notification.dto.NotificationResponseDto;
import efub.assignment.community.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MemberService memberService;
    private final NotificationRepository notificationRepository;

    public Notification createMessageRoomNotice(MessageRoom messageRoom){
        Long memberId = messageRoom.getFirstRecipient().getMemberId();
        String type = "쪽지방";
        String content = "새로운 쪽지방이 생겼어요";
        Notification notification = new Notification(memberId, type,content, messageRoom.getCreatedDate());
        return notificationRepository.save(notification);
    }

    public Notification createCommentNotice(Comment comment){
        Long memberId = comment.getPost().getMember().getMemberId();
        String type = comment.getPost().getBoard().getBoardName();
        String content = "새로운 댓글이 달렸어요 : " + comment.getContent();
        Notification notification = new Notification(memberId,type,content, comment.getCreatedDate());
        return notificationRepository.save(notification);
    }

    // 사용자의 모든 알림 찾기
    @Transactional(readOnly = true) // 피드백 반영 : 조회 전용 메서드에서 성능 개선에 도움
    public List<NotificationResponseDto> findNotificationByMemberId(Long id){
        List<NotificationResponseDto> list = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findAllByMemberId(id);
        for(Notification notic : notifications){
            NotificationResponseDto dto = NotificationResponseDto.from(notic);
            list.add(dto);
        }
        return list;
    }
}

