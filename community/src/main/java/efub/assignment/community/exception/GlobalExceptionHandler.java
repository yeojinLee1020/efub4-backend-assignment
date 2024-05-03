package efub.assignment.community.exception;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomDeleteException.class})
    protected ResponseEntity handlerIllegalAccessException(CustomDeleteException e){
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now().toString(),
                e.getErrorCode().getStatus(),
                e.getErrorCode().name(),
                e.getErrorCode().getMessage()
        );
        return new ResponseEntity(errorDto, HttpStatusCode.valueOf(e.getErrorCode().getStatus()));
    }
}
