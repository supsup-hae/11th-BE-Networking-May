package cotato.backend.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE("잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("존재하지 않는 리소스입니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_API_FAIL("외부 API 호출 실패", HttpStatus.BAD_GATEWAY);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
