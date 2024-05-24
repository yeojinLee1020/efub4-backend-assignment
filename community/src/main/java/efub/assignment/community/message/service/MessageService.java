package efub.assignment.community.message.service;

import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.repository.MessageRepository;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static efub.assignment.community.exception.ErrorCode.NOT_MEMBER_IN_THIS_MESSAGEROOM;


@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberService memberService;
    private final MessageRoomRepository messageRoomRepository;

    // 쪽지 생성
    // 쪽지방의 멤버인지 확인하고 맞으면 메시지 저장, 아니면 예외 발생
    public Message createMessage(Long messageRoomId, MessageRequestDto requestDto) {
        MessageRoom messageRoom = messageRoomRepository.findByMessageRoomId(messageRoomId);
        Member sender = memberService.findMemberById(Long.valueOf(requestDto.getSenderId()));
        if (!isMemberOfMessageRoom(messageRoom, sender)){
            throw new CustomDeleteException(NOT_MEMBER_IN_THIS_MESSAGEROOM);
        }
        Message message = requestDto.toEntity(messageRoom, sender);
        Message savedMessage = messageRepository.save(message);
        return savedMessage;
    }

    // 주어진 멤버가 해당 쪽지방의 멤버인지 확인
    @Transactional(readOnly = true)
    public Boolean isMemberOfMessageRoom(MessageRoom messageRoom, Member member){
        return member.equals(messageRoom.getFirstSender()) || member.equals(messageRoom.getFirstRecipient());
    }

    // 해당 쪽지방의 모든 쪽지 가져오기
    public List<Message> findAllMessageRoomMessage(Long messageRoomId) {
        MessageRoom messageRoom = messageRoomRepository.findByMessageRoomId(messageRoomId);
        return messageRepository.findAllByMessageRoom(messageRoom);
    }

    // 메시지를 보낸 사람인지 확인
    // 메시지를 보낸 사람이라면 true 반환, 아니라면 false
    public Boolean isSender(Message message, Long inquireId) {
        Member inquire = memberService.findMemberById(inquireId);
        return inquire.equals(message.getSender());
    }

}
