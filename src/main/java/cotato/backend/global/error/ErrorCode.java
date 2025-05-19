package cotato.backend.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_INPUT_VALUE("잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
	NOT_FOUND("존재하지 않는 리소스입니다.", HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	EXTERNAL_API_FAIL("외부 API 호출 실패", HttpStatus.BAD_GATEWAY),

	/*LOCAITON*/
	ALREADY_EXISTS_LOCATION("이미 저장된 장소입니다.", HttpStatus.BAD_REQUEST),
	LOCATION_NOT_FOUND("장소를 찾을수 없습니다.", HttpStatus.NOT_FOUND);

	private final String message;
	private final HttpStatus status;

	ErrorCode(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}
}
