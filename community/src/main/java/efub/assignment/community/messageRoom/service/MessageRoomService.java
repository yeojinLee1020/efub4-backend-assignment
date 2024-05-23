package efub.assignment.community.messageRoom.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final MemberService memberService;
    private final PostService postService;

    // 쪽지방 생성
    public MessageRoom createNewMessage(MessageRoomRequestDto requestDto) {
        Member firstSender = memberService.findMemberById(Long.parseLong(requestDto.getFirstSenderId()));
        Member firstRecipient = memberService.findMemberById(Long.parseLong(requestDto.getFirstRecipientId()));
        Post post = postService.findPostById(Long.parseLong(requestDto.getPostId()));
        MessageRoom messageRoom = requestDto.toEntity(post, firstSender,firstRecipient);
        MessageRoom savedMessageRoom = messageRoomRepository.save(messageRoom);
        return savedMessageRoom;
    }

    // 쪽지방 존재 여부 확인 위해 해당 쪽지방 찾는 메서드 (없으면 null)
    public MessageRoom findPresenceOfMessageRoom(Long postId, Long senderId, Long recipientId) {
        Post post = postService.findPostById(postId);
        Member sender = memberService.findMemberById(senderId);
        Member recipient = memberService.findMemberById(recipientId);
        MessageRoom messageRoom = messageRoomRepository.findByPostAndFirstSenderAndFirstRecipient(post, sender, recipient);
        return messageRoom;
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
}
