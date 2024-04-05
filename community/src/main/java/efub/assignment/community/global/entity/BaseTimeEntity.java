package efub.assignment.community.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
 // 엔티티 클래스들이 공통적으로 BaseTimeEntity를 상속하도록 하여,
// 클래스들의 생성시간(createdDate)와 변경시간(modifiedDate)를 기록한다.
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // SpringData JPA에서 자동으로 시간에 대한 값을 넣는 기능을 포함시킨다.
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedDate;
}
