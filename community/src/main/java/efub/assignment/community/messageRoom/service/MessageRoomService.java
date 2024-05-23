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

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final MemberService memberService;
    private final PostService postService;
    public MessageRoom createNewMessage(MessageRoomRequestDto requestDto) {
        Member firstSender = memberService.findMemberById(Long.parseLong(requestDto.getFirstSenderId()));
        Member firstRecipient = memberService.findMemberById(Long.parseLong(requestDto.getFirstRecipientId()));
        Post post = postService.findPostById(Long.parseLong(requestDto.getPostId()));
        MessageRoom messageRoom = requestDto.toEntity(firstSender,firstRecipient,post);
        MessageRoom savedMessageRoom = messageRoomRepository.save(messageRoom);
        return savedMessageRoom;


    }
}
