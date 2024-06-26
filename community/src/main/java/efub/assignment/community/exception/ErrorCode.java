package efub.assignment.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ENTITY_NOT_FOUND(404, "요청하신 ID와 일치하는 객체가 존재하지 않습니다."),
    DELETE_PERMISSION_REJECTED_USER(403, "삭제 권한이 없는 사용자입니다."),
    UPDATE_PERMISSION_REJECTED_USER(403, "수정 권한이 없는 사용자입니다."),
    NOT_MEMBER_IN_THIS_MESSAGEROOM(403, "해당 사용자는 쪽지방의 멤버가 아닙니다.");
    private final int status;
    private final String message;
}