package efub.assignment.community.message.controller;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.AllMessageRoomMessageResponseDto;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.dto.MessageResponseDto;
import efub.assignment.community.message.dto.OneMessageRoomMessageResponseDto;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageRooms/{messageRoomId}/messages")
public class MessageController {

    private final MessageService messageService;
    private final MessageRoomService messageRoomService;

    // 쪽지 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto createMessage(@PathVariable (name="messageRoomId") Long messageRoomId,
                                            @RequestBody MessageRequestDto requestDto){
        Message message = messageService.createMessage(messageRoomId, requestDto);
        return MessageResponseDto.from(message, requestDto.getSenderId());

    }

    // 쪽지 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AllMessageRoomMessageResponseDto getAllMessageRoomMessage(@PathVariable (name="messageRoomId") Long messageRoomId,
                                                                     @RequestParam(name = "inquire_id") Long inquireId){

        Boolean sendOrNot;

        // 1. 해당 메시지방의 모든 메시지 찾아오기
        List<OneMessageRoomMessageResponseDto> list = new ArrayList<>();
        List<Message> messages = messageService.findAllMessageRoomMessage(messageRoomId);
        System.out.println("메시지 개수 " + messages.size());

        // 2. 해당 메시지방의 상대 찾아오기
        Long opponentId = messageRoomService.findOpponentId(messageRoomId, inquireId);


        // 4. 메시지list에 들어갈 메시지들을 dto로 저장하고, list에 추가하기
        for (Message message: messages) {
            // 4-1 메시지 보낸 사람이 조회한 유저인지 확인
            sendOrNot = messageService.isSender(message, inquireId);
            OneMessageRoomMessageResponseDto dto = OneMessageRoomMessageResponseDto.from(message, sendOrNot);
            list.add(dto);
        }

        return new AllMessageRoomMessageResponseDto(messageRoomId, opponentId, list);
    }
}
