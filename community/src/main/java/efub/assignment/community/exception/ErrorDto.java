package efub.assignment.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
