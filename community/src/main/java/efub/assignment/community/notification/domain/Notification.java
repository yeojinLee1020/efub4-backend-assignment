package efub.assignment.community.notification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", updatable = false)
    private Long notificationId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @Builder
    public Notification(Long memberId, String type, String comment, LocalDateTime createdTime) {
        this.memberId = memberId;
        this.type = type;
        this.comment = comment;
        this.createdTime = createdTime;
    }
}
