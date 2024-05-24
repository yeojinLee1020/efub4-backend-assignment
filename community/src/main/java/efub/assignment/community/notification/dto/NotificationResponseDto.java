package efub.assignment.community.notification.dto;

import efub.assignment.community.notification.domain.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponseDto {
    private String type;
    private String content;
    private LocalDateTime createdDate;

    public static NotificationResponseDto from(Notification notification){
        return new NotificationResponseDto(
                notification.getType(),
                notification.getComment(),
                notification.getCreatedTime()
        );
    }
}
