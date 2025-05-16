package cotato.backend.global.error.exception;

import org.springframework.http.HttpStatus;

import cotato.backend.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }
}
