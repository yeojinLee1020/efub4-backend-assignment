package efub.assignment.community.message.controller;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.dto.MessageResponseDto;
import efub.assignment.community.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageRooms/{messageRoomId}/messages")
public class MessageController {

    private final MessageService messageService;

    // 쪽지 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto createMessage(@PathVariable (name="messageRoomId") Long messageRoomId,
                                            @RequestBody MessageRequestDto requestDto){
        Message message = messageService.createMessage(messageRoomId, requestDto);
        return MessageResponseDto.from(message, requestDto.getSenderId());

    }
    // 쪽지 조회
}
