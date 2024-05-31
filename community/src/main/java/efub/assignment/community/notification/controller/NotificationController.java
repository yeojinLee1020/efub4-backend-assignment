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
    public AllNotificationResponseDto getNotification (@PathVariable (name="inquirerId") final Long inquirerId){
        return new AllNotificationResponseDto(notificationService.findNotificationByMemberId(inquirerId));
    }
}
