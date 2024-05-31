package efub.assignment.community.messageRoom.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.notification.domain.Notification;
import efub.assignment.community.notification.service.NotificationService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final NotificationService notificationService;


    // 쪽지방 생성
    public MessageRoom createNewMessage(MessageRoomRequestDto requestDto) {
        Member firstSender = memberService.findMemberById(Long.parseLong(requestDto.getFirstSenderId()));
        Member firstRecipient = memberService.findMemberById(Long.parseLong(requestDto.getFirstRecipientId()));
        Post post = postService.findPostById(Long.parseLong(requestDto.getPostId()));
        MessageRoom messageRoom = requestDto.toEntity(post, firstSender,firstRecipient);
        return messageRoomRepository.save(messageRoom);
    }

    // 쪽지방 존재 여부 확인 위해 해당 쪽지방 찾는 메서드 (없으면 null)
    public MessageRoom findPresenceOfMessageRoom(Long postId, Long senderId, Long recipientId) {
        Post post = postService.findPostById(postId);
        Member sender = memberService.findMemberById(senderId);
        Member recipient = memberService.findMemberById(recipientId);
        return messageRoomRepository.findByPostAndFirstSenderAndFirstRecipient(post, sender, recipient);
    }

    // 내가 있는 모든 쪽지방 조회
    public List<MessageRoom> findAllMessageRoomByMemberId(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        List<MessageRoom> messageRoomWhichISend = messageRoomRepository.findByFirstSender(member);
        List<MessageRoom> messageRoomWhichIReceive = messageRoomRepository.findByFirstRecipient(member);
        // 두 리스트 컬렉션을 합치는데 중복성 제거를 위해 겹치는 것 remove하고 add한 새로운 리스트 컬렉션 만듦
        List<MessageRoom> mergedList = new ArrayList<>(messageRoomWhichISend);
        mergedList.removeAll(messageRoomWhichIReceive);
        mergedList.addAll(messageRoomWhichIReceive);
        return mergedList;
    }

    public void deleteMessageRoom(Long messageRoomId) {
        messageRoomRepository.deleteById(messageRoomId);
    }

    // 쪽지방의 상대방Id 반환
    public Long findOpponentId(Long messageRoomId, Long inquireId) {
        // 파라미터 값이 null인 경우 예외 던지기
        if (messageRoomId == null) {
            throw new NullPointerException("messageRoomId cannot be null");
        }
        if (inquireId == null) {
            throw new NullPointerException("inquireId cannot be null");
        }


        // 파라미터 값들이 null이 아닌 경우 로직 실행
        MessageRoom messageRoom = messageRoomRepository.findByMessageRoomId(messageRoomId);
        Member member = messageRoom.getFirstSender();
        if (member.getMemberId().equals(inquireId)) {
            return messageRoom.getFirstRecipient().getMemberId();
        }
        else {
            return member.getMemberId();
        }
    }
}
