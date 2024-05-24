package efub.assignment.community.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllNotificationResponseDto {
    private List<NotificationResponseDto> NotificationList;
}
