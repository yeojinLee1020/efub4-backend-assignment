package efub.assignment.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomDeleteException extends RuntimeException{
    private final ErrorCode errorCode;
}
