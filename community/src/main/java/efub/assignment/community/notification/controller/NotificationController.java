package efub.assignment.community.notification.controller;

import efub.assignment.community.notification.domain.Notification;
import efub.assignment.community.notification.dto.AllNotificationResponseDto;
import efub.assignment.community.notification.dto.NotificationResponseDto;
import efub.assignment.community.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{inquirerId}")
    public AllNotificationResponseDto getNotification (@PathVariable (name="inquirerId") Long id){
        List<NotificationResponseDto> list = new ArrayList<>();
        List<Notification> notifications = notificationService.findNotificationByMemberId(id);
        for(Notification notic : notifications){
            NotificationResponseDto dto = NotificationResponseDto.from(notic);
            list.add(dto);
        }
        return new AllNotificationResponseDto(list);
    }
}
