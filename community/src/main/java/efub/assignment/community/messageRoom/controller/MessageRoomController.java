package efub.assignment.community.messageRoom.controller;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.*;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageRooms")
public class MessageRoomController {
    private final MessageRoomService messageRoomService;

    // 쪽지방 생성
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageRoomResponseDto createNewMessageRoom(@RequestBody @Valid final MessageRoomRequestDto requestDto){
        MessageRoom savedMessageRoom = messageRoomService.createNewMessage(requestDto);
        return MessageRoomResponseDto.from(savedMessageRoom, savedMessageRoom.getFirstSender().getMemberId(),
                savedMessageRoom.getFirstRecipient().getMemberId());
    }

    // 쪽지방 여부 조회
    @GetMapping("/{postId}/{senderId}/{recipientId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MessageRoomStatusResponseDto MessageRoomExists(@PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "senderId") Long senderId,
                                                          @PathVariable(name = "recipientId") Long recipientId){
        MessageRoom messageRoom = messageRoomService.findPresenceOfMessageRoom(postId, senderId, recipientId);
        return new MessageRoomStatusResponseDto(messageRoom);
    }

    // 쪽지방 목록 조회 (사용자의 모든 쪽지방 조회)
    @GetMapping("/{memberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MessageRoomListResponseDto getMessageRoomList(@PathVariable(name = "memberId") Long memberId){
        List<OneMessageRoomResponseDto> list =  new ArrayList<>();
        List<MessageRoom> messageRooms = messageRoomService.findAllMessageRoomByMemberId(memberId);
        for(MessageRoom mr : messageRooms){
            OneMessageRoomResponseDto dto = OneMessageRoomResponseDto.from(mr);
            list.add(dto);
        }
        return new MessageRoomListResponseDto(list);
    }

}
